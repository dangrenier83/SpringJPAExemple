# Exemple Spring Boot avec Spring Data JPA 

## Préparation de VS Code pour la programmation Spring

Nous devons installer les extensions suivantes (en plus de celles pour Java) pour Visual Studio Code: 
* Spring Boot Extension Pack (Spring Boot Tools, Spring Initialzr Java Support, Spring Boot Dashboard)
* Spring Boot Support
* Thymeleaf Snippets
* Lombok Annotations Support for VS Code

![1](https://user-images.githubusercontent.com/87398047/191582943-60718cca-81f0-44b6-a9a7-9ff37e17249a.png)

## Création du projet avec Spring Boot Initialzr

* Fermer votre workspace de Visual Studio Code et cliquer sur Create Java project
![2](https://user-images.githubusercontent.com/87398047/191583640-9268be03-f9ea-496e-aa04-0b93ca8a4e16.png)

* Vous aurez ensuite les choix suivants: ![3](https://user-images.githubusercontent.com/87398047/191583776-10dc1f9b-dbb1-4ddf-ae82-b9743bb91250.png)

  Sélectionnez Spring Boot > Maven Project > 2.7.3 > Java

  Ensuite l'initialisateur Spring Boot nous demandera de spécifier un nom de package pour notre projet (Group Id) - exemple `com.julexmile`
  Ensuite il nous demandera de spécifier un Artifact Id (le nom du projet) exemple `exemplejpa`

  Nous devons ensuite sélectionner entre un JAR et un WAR pour le packaging type de notre exécutable. Nous allons sélectionner JAR car nous allons utiliser le templating engine Thymeleaf.

  Nous allons choisir la version 11 de Java

  Ensuite nous arrivons aux choix des "starter dependencies" de notre projet Spring Boot
![4](https://user-images.githubusercontent.com/87398047/191583912-4831c84e-33f8-41b0-a026-ba46da4d2081.png)


  ## Choix des dependencies

  Choississons: 

  - Spring Boot DevTools (utilitaires facilitant le développement d'une application Spring)
  - Spring Web (contient Spring MVC et les librairies utiles pour le développement d'un site web)
  - Lombok (Librairie utile pour la génération des getters/setters/constructeurs/Slf4j)
  - Thymeleaf (Le templating engine que nous allons utilisé, ressemble un peu à JSTL dans un JSP mais plus étendu)
  - JDBC API, Spring Data JPA, Spring Data JDBC
  - Oracle Driver puisque nous allons utiliser une base de données Oracle 11


![5](https://user-images.githubusercontent.com/87398047/191583990-7c622032-8a22-4c7a-9809-e6f7c40e8623.png)
  Ensuite il nous demande de sélectionner le chemin du dossier où nous sauvegarderons notre projet

  Nous pouvons ensuite l'ouvrir en cliquant sur Open

![6](https://user-images.githubusercontent.com/87398047/191584094-a9f5df95-3f1e-4510-ad3e-8339d7c6ae5b.png) 


 ## Configuration de notre projet Spring avec notre base de données

 Remplacez le application.properties dans src/main/resources pour application.yml
 contenant votre configuration SQL, ex: (Faites bien attention l'indentation détermine la hiérarchie des propriétés dans un fichier YAML)

```
spring:
  jpa:
    database-platform: org.hibernate.dialect.Oracle9Dialect
    hibernate:
      ddl-auto: create

  datasource:
      url: jdbc:oracle:thin:@//localhost:1521/xe
      username: HR 
      password: HR 
      driver-class-name: oracle.jdbc.driver.OracleDriver

logging:
    level:
      '[org.hibernate.SQL]': DEBUG
      '[orb.hibernate.type.descriptor.sql.BasicBinder]': TRACE
```

## Création de l'entité Docteur

- Créez un dossier modèle dans src/main/java/com/julexmile/exemplejpa/
- Créez un fichier Docteur.java

```
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Docteur {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_doctor")
    @SequenceGenerator(name = "gen_doctor", sequenceName = "SEQ_DOCTOR", allocationSize = 1)
    private Long id;

    private String firstName;
    private String lastName;
    private String city;
    
}
```

L'annotation @Data dit à Lombok de créer les getters et les setters pour tout les attributs de l'entité.
@NoArgsConstructor lui dit de créer un constructeur vide et @AllArgsConstructor pour le constructeur contenant tout les attributs.

Nous feront également un constructeur sans id :

```
    public Docteur(String firstName, String lastName, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
    }
```

## Création du JPA Repository

- Créez un dossier data dans src/main/java/com/julexmile/exemplejpa/
- Crééz une interface DocteurRepository

```
public interface DocteurRepository extends CrudRepository<Docteur, Long> {

    Docteur findByUsername(String username);
    
}
```

- Cette interface implémente les opérations courantes d'un CRUD automatiquement en héritant de CrudRepository tels que:

	* Docteur findById()
	* Iterable<Docteur> findAll()
	* les delete, save, etc.

	Par défaut, il les génère toutes pour le @Id 

	Nous pouvons également vouloir spécifier des méthodes custom en utilisant une formulation camel-case (attention vos attributs
	d'entités doivent être en CamelCase ou cela pourrait mener à des problèmes dans la compréhension du Crud Repository de Spring Data JPA pour
	l'implémentation des méthodes). 

## Création d'un controlleur de base
Nous aurons besoin de 4 controllers soit :

- MenuController.java : redirige `localhost:8080/` vers le template représentant le menu de base
- DocteurListController.java : Permet l'affichage des docteurs à l'adresse `localhost:8080/docteurlist`. Prend également des paramètres optionnels
dans l'URL soit act (modify ou delete) et user pour savoir sur quelle entité effectuer l'action.
- AddDoctorController.java : Ajoute un docteur à l'aide des données du formulaire dans ajoutdocteur.html et redirige vers la liste
- ModifyDoctorController.java : Modifie un docteur à l'aide des donnée du formulaire dans modifydocteur.html

## Création des vues à l'aide de templates Thymeleafs
- Dans notre programme, les controlleurs renvoient des String du même nom qu'une vue Thymeleaf, les données y sont rendues disponibles
par l'ajout d'objet nommés dans la modèle.
- Les données issues d'un formulaire ou les paramètres optionnels correspondant aux actions modify et delete sont accédées grâce à l'annotation
@RequestParam
- Le templating engine Thymeleaf permet de nombreuses opérations (des boucles, accès aux données, conditionnels etc.) à celles présentes dans JSTL
## Ajout de données de base à l'aide d'un CommandLineRunner

* Après l'avoir roulé une fois et avoir créé le modèle de données et populé votre database vous pouvez changer 
ddl-auto dans le fichier application.yml de create vers update.




