# processor-bibliography

Processeur générant des constantes bibliographiques à partir d'un fichier Json.

## Préparation de la bibliographie

La bibliographie est constituée d'une classe Java annotée `@Bibliography` et d'un fichier Json nommé comme la classe
mais en bas de casse et situé dans les ressources du module Maven, dans la même hiérarchie de répertoires/paquetages.

Par exemple :

Dans `src/main/java/com/cosmoloj/bibliography/astroloj/Astroloj.java` :

```java
@Bibliography
public class Astroloj {
}
```

Dans `src/main/resources/com/cosmoloj/bibliography/astroloj/astroloj.json` :

```json
[
    {
        "type": "institution",
        "key": "nasa",
        "title": "NASA"
    },
    {
        "type": "institution",
        "key": "iau",
        "title": "International Astronomic Union"
    }
]

```


## Liste des clefs par type de ressource

- article
  - title
  - subtitle
  - pages
  - issue
  - volume
  - month
  - year
  - url

- web
  - title
  - institution
  - url

- phdthesis
  - title
  - year
  - url

- institution
  - title
  - url

- book
  - title
  - editor
  - year
  - url

- techreport
  - kind (NORM, STANDARD, SPECIFICATION, RECOMMENDATION, DOCUMENT, REPORT, THESIS)
  - title
  - number
  - version
  - year
  - url

- journal
  - title
  - issn
  - eIssn
  - url

- collection
  - title
  - issn
  - eIssn
  - url


## Application du préprocesseur sur la bibliographie

Le préprocesseur est exécuté lors de la construction du projet et génère une classe Java contenant les constantes de
la bibliographie mais *package private*.

```java
class AstrolojBibliography {

    protected AstrolojBibliography() {
    }


    @Institution(title = "NASA")
    public static final String NASA = "nasa";

    @Institution(title = "International Astronomic Union")
    public static final String IAU = "iau";

    // la suite...
}
```

Pour accéder à la bibliographie, il suffit d'étendre la classe générée par la classe annotée comme suit :

```java
@Bibliography
public class Astroloj extends AstrolojBibliography {
}
```

On peut alors utiliser la bibliographie dans le code source. Par exemple :

```java
@Reference(Astroloj.IAU)
public class MaClasse implements MonInterface {
    // le contenu de la classe
}
```
