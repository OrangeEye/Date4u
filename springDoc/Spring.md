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