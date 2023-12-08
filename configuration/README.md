# Configuration

## Implémentation de l'interface *Configurable*

L'implémentation de cette interface requiert :

1. d'implémenter la méthode de définition des propriétés de la configuration
2. d'implémenter la méthode de chargement de la configuration

Par exemple :

```java
public class CatalogI239ReaderTest implements Configurable {

    private static final String I239_PATH = "i239.catalog.data";
    private static final Map<String, String[]> CONFIGURATION = Map.of(
            I239_PATH, new String[]{"chemin vers le catalogue I239"});

    @Override
    public Map<String, String[]> configurationParameterMap() {
        return CONFIGURATION;
    }

    @Override
    public Configuration getConfiguration() {
        return Configuration.of(".astroloj", "configuration-test.properties");
    }

    final CatalogReader<StarI239> reader = new CatalogI239Reader();

    final Map<Integer, StarI239> map = new HashMap<>();

    public CatalogI239ReaderTest() throws IOException {
        final String ephemerisPath = getConfigurationParameterValue(I239_PATH);
        Assumptions.assumeTrue(ephemerisPath != null, "pas de chemin des éphémérides dans la configuration");
        final var file = new File(getConfigurationParameterValue(I239_PATH), "hip_main.dat");
        Assumptions.assumeTrue(file.exists(), "le fichier des éphémérides n'existe pas");
        try (var is = new FileInputStream(file)) {
            reader.read(is, s -> map.put(s.identifier(), s));
        }
    }

    @Test
    public void test_size() {
        Assertions.assertEquals(118218, map.keySet().size());
    }
}
```

Quelques configurations d'usage.


1. Une configuration de test sous forme d'un fichier `.properties` dans le répertoire `.astroloj` :

```java
Configuration.of(".astroloj", "configuration-test.properties")
```

2. Une configuration sous forme d'un fichier par defaut `configuration.properties` dans le répertoire `.astroloj` :

```java
Configuration.of(".astroloj")
```

L'implémentation de la configuration par défaut :

1. utilise des propriétés relatives au nom de la classe courante
2. recherche successivement la valeur de la propriété dans les propriétés système, dans les variables d'environnement et dans le fichier de configuration.

## Faire tourner les tests avec une configuration de test

On peut par exemple ajouter dans la configuration Netbeans du *goal* `mvn clean install` la configuration suivante :

```
astroloj.configuration.path=/home/samuel/.astroloj
astroloj.configuration.file=configuration-test.properties
```