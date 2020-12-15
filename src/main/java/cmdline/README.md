# Ligne de commande

Ce package contient l'interface et les classes d'implémentation pour les lignes de commande.

Le contenu du sous-package __api__ est stable et ne devrait plus être modifié.

## Command line
 Toute classe voulant gérer une ligne de commande doit implémenter l'interface __api.CommandLine__.
 La méthode __execute__ est la seule à définir. Elle prend en paramètre un argument qui représente 
 la ligne de commande déjà parsée, grâce à la bibliothèque JCommander.
 Par exemple, la classe __impl.size.CommandSize__ gère la commande ___size___.
 
## Command line provider
 L'interface __api.CommandLineProvider__ fournit une API standard à implanter par les classes fournisseuses (Builder)
 d'objets de type __api.CommandLine__. Par exemple, pour la commande __impl.size.CommandSize__, 
 la classe fournisseuse est __impl.size.CommandeSizeProvider__. 

## Command line loader
La classe __api.CommandLineLoader__ utile ServiceLoader de java pour charger les classes fournisseuses
 de __api.CommandLine__, c-à-d les __api.CommandLineProvider__. Pour fonctionner, ServiceLoader doit connaître
 la déclaration des fournisseuses dans le fichier cmdline.api.CommandLineProvider situé dans 
 __src/main/resources/META-INF/services/__.
 
 Par exemple, la classe __impl.size.CommandeSizeProvider__ est déclarée dans le fichier
 __src/main/resources/META-INF/services/cmdline.api.CommandLineProvider__, de telle sorte que 
 ServiceLoader puisse la découvrir et la charger automatiquement. C'est ainsi que le programme principal 
 (__client.App__) charge toutes les classes fournisseuses de type __api.CommandLineProvider__ 
 sans les connaître d'avance et les utilise pour récupérer les objets de type __api.CommandLine__ qu'elles créent. 
 
 
 ## Déclaration d'une nouvelle commande
 Toute nouvelle classe implémentant __api.CommandLine__ doit donc être disposée dans un sous-package
 indépendant (sous __cmdline.impl__) avec en plus :
 - la classe implémentant __CommandLineProvider__ et qui la crée ;
 - la déclaration du provider dans __src/main/resources/META-INF/services/cmdline.api.CommandLineProvider__ 
 en rajoutant une ligne indiquant le nom qualifié complet du provider ;
 - éventuellement le director du builder qui crée le visiteur associé à cette commande.
 
 Le visiteur pour la nouvelle commande doit être disposé dans le package __visitors.impl__.
 Ce visiteur doit implanter l'interface visitors.api.Visitor.
 
 L'API du builder du visiteur doit être déclaré dans le package __visitors.api__ et son implémentation
 dans le même package que ce visiteur (visitors.impl).
 
 
   