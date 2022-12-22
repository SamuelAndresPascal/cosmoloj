# Simple Unit (implémentation Java - implémentation de référence)

## Utilisation

Utilisation des unités transformées :

```java
final Unit m = new SimpleFundamentalUnit();
final Unit km = m.scaleMultiply(1000);
final Unit cm = m.scaleDivide(100);
final UnitConverter cmToKm = cm.getConverterTo(km);

cmToKm.convert(3); // 0.00003
cmToKm.inverse().convert(0.00003); // 3
```

Utilisation des unités dérivées :

```java
final Unit m = new SimpleFundamentalUnit();
final Unit km = m.scaleMultiply(1000);

final Unit km2 = SimpleDerivedUnit.of(km.factor(2));
final Unit cm = m.scaleDivide(100);
final Unit cm2 = SimpleDerivedUnit.of(cm.factor(2));
final UnitConverter km2Tocm2 = km2.getConverterTo(cm2);

km2Tocm2.convert(3); // 30000000000
km2Tocm2.inverse().convert(30000000000); // 3
```

Utilisation des unités dérivées en combinant les dimensions :

```java
final Unit m = new SimpleFundamentalUnit();
final Unit kg = new SimpleFundamentalUnit();
final Unit g = kg.scaleDivide(1000);
final Unit ton = kg.scaleMultiply(1000);
final Unit gPerM2 = SimpleDerivedUnit.of(g, m.factor(-2));
final Unit km = m.scaleMultiply(1000);
final Unit tonPerKm2 = SimpleDerivedUnit.of(ton, km.factor(-2));
final Unit cm = m.scaleDivide(100);
final Unit tonPerCm2 = SimpleDerivedUnit.of(ton, cm.factor(-2));
final UnitConverter gPerM2ToTonPerKm2 = gPerM2.getConverterTo(tonPerKm2);
final UnitConverter gPerM2ToTonPerCm2 = gPerM2.getConverterTo(tonPerCm2);

gPerM2ToTonPerKm2.convert(1); // 1
gPerM2ToTonPerKm2.inverse().convert(3); // 3
gPerM2ToTonPerCm2.convert(1); // 1e-4
gPerM2ToTonPerCm2.convert(3); // 3e-10
gPerM2ToTonPerCm2.offset(); // 0.0
gPerM2ToTonPerCm2.scale(); // 1e-10
gPerM2ToTonPerCm2.inverse().offset(); // -0.0
gPerM2ToTonPerCm2.inverse().convert(3e-10); // 3
```

Utilisation des températures (conversions affines et linéaires) :

```java
final Unit k = new SimpleFundamentalUnit();
final Unit c = k.shift(273.15);
final UnitConverter kToC = k.getConverterTo(c);

kToC.convert(0) // -273.15
kToC.inverse().convert(0) // 273.15

// en combinaison avec d'autres unités, les conversions d'unités de températures doivent devenir linéaires
final Unit m = new SimpleFundamentalUnit();
final Unit cPerM = SimpleDerivedUnit.of(c, m.factor(-1));
final Unit kPerM = SimpleDerivedUnit.of(k, m.factor(-1));
final UnitConverter kPerMToCPerM = kPerM.getConverterTo(cPerM);

kPerMToCPerM.convert(3); // 3
kPerMToCPerM.inverse().convert(3); // 3
```

Utilisation des conversions non décimales :

```java
final Unit m = new SimpleFundamentalUnit();
final Unit km = m.scaleMultiply(1000.);

final Unit s = new SimpleFundamentalUnit();
final Unit h = s.scaleMultiply(3600.);

final Unit ms = SimpleDerivedUnit.of(m, s.factor(-1));
final Unit kmh = SimpleDerivedUnit.of(km, h.factor(-1));

final UnitConverter msToKmh = ms.getConverterTo(kmh);

msToKmh.convert(100.); // 360.
msToKmh.inverse().convert(18.); // 5
```