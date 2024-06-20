# Spring

## Spring-Beans

Um mit dem Spring-Framework neue Beans zu initialisieren, müssen die entsprechenden Klassen mit `@Component` annotiert
werden. `@Component` ist die allgemeinste Annotation, ohne Semantischen Wert. Es gibt weitere Annotationen mit
semantischen Wert. Es ist möglich, dass in in späteren Spring Versionen zusätzliche Eigenschaften zu den Komponenten
hinzufügt.

- `@Service`-Klassen führen Businesslogik aus
- `@Repository`-Klassen stellen eine Verbindung zum Datenspeicher / Datenbanken dar
- `@Controller`-Klassen nehmen Anfragen Entgegen (GUI/UI).

Die Startklasse ist mir @SpringBootApplication annotiert, welches sich aus den folgenden Annotionen zusammensetzt

- `@SpringBootConfiguration` -> `@Configuration` -> `@Component`
- `@EnableAutoConfiguration`
- `@ComponentScan` -> Durchsucht in Kombination mit @Configuration alle Unterpakete nach Komponenten

In `@ComponentScan` können die basePackages definiert werden, wo nach Komponenten gesucht wird. Dann werden jedoch auch
nur noch in diesen Paketen gesucht.

## Proxies

Per Default baut eine `@Configuration`-Klasse ein Proxy um die Bean herum und um die `@Bean`-Methoden. Der Proxy führt
bei
mehrfachen Aufruf der Bean-Fabrikmethode diese nicht mehr aus. Stattdessen gibt er die bereits gebaute Bean von der
Fabrikmethode zurück. Der Proxy muss in den meisten Fällen jedoch nicht aufgebaut werden, wenn die Fabrikmethoden nicht
manuell aufgerufen werden. Dies kann man mit `@Configuration(proxyBeanMethods = false)` vermeiden und sollte nach
Möglichkeit immer verwendet werden.

In der Logausgabe kann man einen Proxy erkennen an der Bezeichnung `SpringCGLIB$`.

## Dependency-Injection

Es gibt drei Arten von Dependency-Injection in Spring:

- Constructor-Injection: Ist die bevorzugte Variante. Macht deutlich, welche Referenzen benötigt werden (Setter drücken
  die Abhängigkeit nicht aus). Große Parameterliste deutet auf ein Anti-Pattern hin (Gott-Objekt). `@Autowired` ist
  optional, kann aber für die Dokumentation besser sein.
- Setter-Injection: Es können keine finalen Attribute gesetzt werden
- Field-Injection: Kurze Schreibweise, kann nicht final sein

Es können alle Arten zusammen verwendet werden.

### Injection auf Basistyp

Es können mehrere Beans von einem Typ oder einem Basistyp existieren. Bei der Injizierung wird keine Bean zufällig
ausgewählt. Zur Bestimmung der Bean gibt es mehrere Möglichkeiten.

1. `@Qualifier(BEAN_NAME)` bei der Injection verwenden, um die Bean zu referenzieren. Dabei sollte man der Bean einen
   eigenen Namen über eine Konstante geben.
2. Die Beans über eine eigene Annotation qualifizieren, die die `@Quality` als Meta-Annotation verwendet (Bsp.
   ThumbnailRendering)
3. `@Primary` bei einer Bean verwenden. Sie wird dann verwendet, wenn keine spezifische Bean qualifiziert wurde.
4. Alle Beans verwenden in einer Datenstruktur. zB `List<Thumbnail>`, `Set<Thumbnail>`, `Thumbnail[]`
5. Fallback-Match: Eine Variablenbezeichnung verwenden, die identisch mit dem Bean-Namen ist. Sollte nicht verwendet
   werden.
6. Die `@Ressource(name="myComponent"` Annotation aus dem Jakarta Paket verwenden. Diese qualifiziert nicht nach dem
   Typ, sondern über den Namen der Komponente. Wird dieser nicht mit angegeben, wird dieser aus der Variablenbezeichnung
   gezogen. Ist die Alternative zu `@Qualifier`.

### Keine oder mehrere verfügbare Beans

Wenn zur Laufzeit nicht klar ist, ob es mehrere oder gar keine Bean zu Verfügung gibt, gibt es mehrere Wege damit
umzugehen

1. `Optional<MyComponent>` enthält eine Bean oder gar keine
2. `@Bean Supplier<MyComponent> supplier(){...}` Eine `java.util.Supplier`-Komponente definieren, welche einem eine
   Instanz erzeugt. (In welchem Fall braucht man das?)
3. `ObjectProvider<MyComponent>` eine Variante der `ObjectFactory<T>` welche zusätzliche Funktionen zu Verfügung
   stell.t `getAvailable()`, `getIfAvailable()`, `getIfUnique()`, `stream()`. Somit kann man prüfen, ob eine oder
   mehrere Komponenten vorhanden sind und entsprechend reagieren. Die Reihenfolge bei mehreren Komponenten wird
   über `@Order` bestimmt, wobei die kleine Order-Zahl die höchste Priorität hat. Alternativ dazu kann die
   Komponenten `Ordered`implementieren und `getOrder()` überschreiben.

## Verhalten bei Vererbungsbeziehungen von Spring-Klassen

Wenn eine `@Component`-Klasse von einer Klasse erbt, die eine `@Bean`-Methode hat, wird diese
auch aufgerufen und eine (Singleton) Bean erzeugt. Existieren mehrere Untertypen, wird die Fabrikmethode dennoch nur
einmal ausgeführt. Die `@Bean`-Methode kann auch eine Default-Methode eines Interfaces sein.

Annotationen können auf eine Unterklasse übertragen werden, sofern die Annotation `@Inherit` gesetzt ist. Dies ist
bei `@SpringBootApplication` und `@EnableAutoConfiguration`. `@Component` besitzt kein `@Inherit`, wodurch eine
Unterklasse nicht automatisch zu einer Komponente wird.

Die `@Autowired`-Attribute werden bei einer Unterklasse ebenfalls injiziert.

## Lebenszyklus der Beans

Die Initialisierung der Beans ist bei Konstruktoren fest vorgegeben. Anders sieht das bei Beans aus Fabrikmethoden aus.

### Abhängigkeit

Mit `@DependsOn()`lassen sich Beans vor anderen Beans initialisieren. Dadurch kann eine Reihenfolge der Initialisierung
erzeugt werden. Bei `@Autowired`-Komponenten ist die Reihenfolge der Initialisierung aber oft schon klar, nicht so wie
bei Field- oder Methoden-Injections. Das `@DepensOn()` ist jedoch von aussen nicht direkt sichtbar, dass da eine
Abhängigkeit ist (für Testfälle).

### Lazy Initialization

Beim Starten der Anwendung werden alle Beans initialisiert. Gibt es ein Fehler beim Aufbau der Komponenten, ist dieser
direkt beim Start und nicht erst irgendwann zur Laufzeit. Dadurch entsteht jedoch eine langsamere Startzeit und mehr
Speicherbedarf, wenn alles direkt aufgebaut wird.

Best Practice: In Produktion alles beim Starten der Anwendung initialisieren. Beim Entwickeln kostet der häufige
Programmstart viel Zeit, daher bei der Entwicklung Lazy Loading verwenden.

1. `spring.main.lazy-initialization=true` lässt global alle Komponenten Lazy initialisieren
2. `@Lazy` an eine Komponente annotieren. Überschreibt für diese Komponente die globale Einstellung

Die Komponente wird im Context zwar aufgenommen, aber sie wird noch nicht voll initialisiert (Konstruktor werden nicht
aufgerufen).

## Security

Ein angemeldeter User wird durch das Interface `Authentication` dargestellt. `SecurityContext`wird seinerseits dazu
verwenden, die `Authentication` zu lesen und zu speichern. Die Klasse `SecuritiyContextHolder` gewährt über statische
Methoden den Zugriff auf `SecurityContext.` (zB. `SecuritiyContextHolder.getContext()`). Dieser ist ThreadLocal, sodass
er nur im selben Ausführungsthread zur Verfügung steht. Somit ist eine `Authentication` einem Thread zugeordnet.

Es gibt verschiedene Implementierungen von `Authentication` zB. `UsernamePasswordAuthenticationToken`
und `BearerTokenAuthentication`

### Prüfen der Authentifizierung

Ein `AuthenticationManager` kann prüfen, ob eine `Authentication` gültig ist. Aktuell gibt es den `ProviderManager`
welcher `AuthenticationManager` implementiert und eine Liste von `AuthenticationProvider`besitzt. Einer
der `AuthenticationProvider` übernimmt die Aufgabe der Authentifizierung, sofern er diesen Typ der `Authentication`
authentifizieren kann.

Einer der `AuthenticationProvider` ist der `DaoAuthenticationProvider`, welcher Benutzername und Passwort
authentifizieren kann. Zur Prüfung verwendet er einen `UserDetailsService`mit einem `PasswordEncoder`.

Der `UserDetailsService` kann mit der Methode `loadUserByUsername(u: String)` eine `UserDetails` zurückgeben, welches
den User repräsentiert (sofern er vorhanden sind). `UserDetais` und `Authentication` haben ähnliche Daten,
doch `UserDetails` dient nur zur Prüfung ob der User existiert bzw. authentifiziert werden kann. Der `Authentication`
repräsentiert den angemeldeten User.

Per default baut Spring Security über `UserDetailsServiceAutoConfiguration` eine `UserDetails` auf ( Genauer gesagt die
Implementierung `InMemoryUserDetailsManager`, indem die User nur im Memory gespeichert werden). Dort gibt es einen User
namens "User", welcher ein default Passwort bekommt (wird in der Konsole ausgegeben). Alternativ kann dieser über
Properties gesetzt werden:
`spring.security.user.name=MyUser`
`spring.security.user.password=MyPasswort`
Nur in der Entwicklung verwenden. Ansonsten zB den `JdbcUserDetailsManager` oder `LdapUserDetailsManager`

Damit kann man sich bereits über den Browser anmelden und ein Cookie wird ausgetauscht. Über `/logout` kann man sich
wieder abmelden.

Um nun die User anzugeben, kann eine `UserDetailsService` Bean erzeugt werden, welche der `DaoAuthenticationProvider`
dann verwendet um die vorhandenen User abzufragen.

### PasswordEncoder

Der `PasswordEncoder` wird von verschiedenen Klassen implementiert wie `NoOpPasswordEncoder`
und `BCryptPasswordEncoder`. Dabei verwenden verschiedene Hash-Algorithmen verwendet. Der `DelegatingPasswordEncoder`
delegiert die Prüfung des Passworts anhand des Präfixes an die entsprechende Klasse.

- {noop} -> `NoOpPasswordEncoder`
- {bcrypt} -> `BCryptPasswordEncoder`
- {pbkdf2} -> `Pbkdf2PasswordEncoder`
- {scrypt} -> `SCryptPasswordEncoder`
- {sha256} -> `StandardPasswordEncoder`

Wenn immer nur das Gleiche Encoding verwendet wird, kann durch eine `PasswordEncoder` Bean der Encoder direkt bestimmt
werden. Dann darf allerdings der Präfix nicht mehr verwendet werden.

### bcrypt

bcrypt ist ein Hashverfahren, welches nicht umkehrbar ist und sicher gegen Bruteforce ist. Gehashte Passwörter beginnen
immer mit einem `$` und der Kennung des Algorithmus. Bei `bcrypt`wird die Anzahl der Runden angegeben, die für eine
Prüfung notwendig sind. Darauf folgt ein Zufallswert und das gehashte Passwort.

`$2a$15$J.vEW0VdUPSSXDdEzi1sqOWbX2M/wd0sPAP/Y.7TL1FURMfTqM4Ii`

# Typo Buch

9.17.3 Das Interface heisst Authentication, nicht Authentification
9.17.9 Abschnitt DelegatingPasswordEncoder -> Das Passwort ist doch "mk8suwi4kq" und nicht "1234"?
9.17.9 Abschnitt bcrypt -> Absatz "Der Strings beginnen immer ..." Typo: Die Strings 