# Opérations sur un système de fichiers

Ce projet fait principalement appel aux patrons de conception Builder, Composite, Iterator et Visitor.

L'objectif de cet exercice est de créer un logiciel s'inspirant de __[cloc](https://github.com/AlDanial/cloc)__.
Il existe d'autres logiciels similaires, comme __[tokei](https://github.com/XAMPPRocky/tokei)__.

Nous en ferons ici une version très simplifiée.

## Exécution
Cette section montre des exemples d'utilisation de ce logiciel.

__NB :__ Les tailles affichées dans les tableaux ci-après, résultants de l'exécution 
de chaque commande, seront différentes dans votre environnement, et chaque fois que
vous aurez modifié, ajouté ou supprimé des fichiers.
 
### Commande SIZE
Pour lancer ce programme dans le terminal, en utilisant gradle 
(dans ce qui suit, __gradle__ peut être remplacé par __./gradlew__) : 
```bash
gradle run --args='size -h'
```

La commande ci-dessus lance le programme avec les arguments ___size -h___. Cela signifie que du point de vue
de cet exercice, nous avons lancé la ligne de commande __size__ avec __-h__ comme option. 
Cela affichera l'aide de la commande __size__.

Exemple 1 : 
```bash
gradle run --args='size -f src'
```
Dans l'exemple ci-dessus, la commande size est lancée avec comme option __-f__ et comme
arguments à cette option __src__. 
Cette commande affiche la taille de chaque fichier dans l'arborescence de __src__ (en octets).

__NB :__ L'option __-f__ est requise, contrairement aux autres.


Exemple 2 : 
```
 ./gradlew run --args='size -f src -u KB'   
```
Dans la commande ci-dessus, l'option __KB__ est indiquée avec __-u__ pour spécifier l'unité
d'affichage des tailles (KB = Kilo-octets).

Bytes (octets) est l'unité par défaut si rien n'est spécifié, comme dans la 
commande précédente.

Exemple 3 :
```bash
gradle run --args='size -u KB -ds -f src'   
```    
La commande ci-dessus lance le programme avec les arguments ___size -u KB -ds -f src___.
Du point de vue de cet exercice, nous avons lancé la ligne de commande __size__ avec comme options
__-u KB__ et __-ds__, et comme argument (requis) le répertoire __src__ via -f.
Cette commande affiche les tailles des fichiers en Kilo-octets (KB) via l'option __-u KB__.
Elle inclut les répertoires dans le calcul du total, tout en affichant la taille propre de chaque répertoire
(option __-ds__).
  


## Build
La création d'un jar de ce logiciel s'effectue par l'invocation de la cible jar via gradle :
````bash
gradle jar
````

Le (uber) jar produit se trouvera dans le répertoire __build/libs/__. Ce jar contient toutes les dépendances nécessaires
à l'exécution. 

### Exécution du jar avec la commande SIZE

Pour lancer ce programme via son jar et non plus via gradle 
(remplacer __chemin/vers/nom_du_fichier.jar__ par le vrai chemin vers le jar) 
voici un exemple d'utilisation du jar pour la commande SIZE :

```bash
java -jar chemin/vers/nom_du_fichier.jar size -u KB -fs -f src
```

La commande est lancée au même niveau que le répertoire __src__.

## Bibliothèques utiles

### Parser les options et les arguments
Nous utilisons la bibliothèque __[JCommander](https://jcommander.org/)__ pour spécifier et parser les options et les 
arguments.    

### Détecter les types de fichier
Pour vous aider à détecter les types de fichier, vous pouvez faire appel à la bibliothèque 
__[Apache Tika](https://tika.apache.org/1.4/formats.html)__.

Voir aussi : __[https://dzone.com/articles/determining-file-types-java](https://dzone.com/articles/determining-file-types-java)__

La page __[suivante sur StackOverflow](https://stackoverflow.com/questions/51438/getting-a-files-mime-type-in-java__)__ 
discute abondamment de la question.


### Compresser des fichiers
Pour compresser des fichiers vous pouvez faire appel à :

- un package spécifique dans le JDK : __[java.util.zip](https://www.baeldung.com/java-compress-and-uncompress)__
- une bibliothèque tierce-partie comme __[Zip4j](https://github.com/srikanth-lingala/zip4j)__
- une autre bibliothèque comme __[zt-zip](https://github.com/zeroturnaround/zt-zip)__

## Pour aller plus loin
- Vous trouverez dans __[cet article](https://boyter.org/posts/sloc-cloc-code/)__ sur __[https://boyter.org](https://boyter.org/)__
une discussion intéressante sur le comptage de lignes de codes et un benchmark des outils existants.
- Interactive console applications in Java, __[article sur DZone](https://dzone.com/articles/interactive-console-applications-in-java)__