# management-system

The aim of the project is to create a web application for managing projects, divided into back-end and front-end. Additionally, it utilizes the Spring Boot framework and Gradle as the build tool.

The tasks that the web application fulfills include:
- Securing data and the application against unauthorized access.
- Implementing a data transmission encryption protocol.
- Providing separate permissions for the instructor and students.
- Conducting unit and integration testing.
- Offering full functionality that allows adding, modifying, and deleting data for projects, tasks, and students.
- Implementing pagination, searching, and sorting of project and student data.
- Enabling file uploads to the server and downloads of files associated with specific projects or tasks.
- Providing a public chat that uses a bidirectional WebSocket channel.
- Implementing a registration mechanism within the application.

---

https://trello.com/b/9RUZ5X2m/22032023
---
### Realizacja powinna uwzględniać m.in.:
- zabezpieczenie danych i aplikacji przed niepożądanym dostępem, 
- protokół szyfrowania transmisji danych (w tym celu należy wygenerować certyfikat SSL np. za pomocą narzędzia keytool dostępnego w każdym JDK),
- odrębne uprawnienia dla prowadzącego i studentów (dla nas administrator),
- testy jednostkowe i integracyjne (koniecznie serwisów i kontrolerów, zalecane JUnit5, Mockito i MockMVC),
- pełną funkcjonalność systemu pozwalającą dodawać, modyfikować i usuwać dane projektów, zadań i studentów. Powinna istnieć możliwość stronicowania i wyszukiwania, opcjonalnie sortowania, danych projektów i studentów.
- możliwość przesyłania na serwer i pobierania plików przypisywanych do danego projektu lub zadania, 
- ogólnodostępny chat korzystający z dwukierunkowego kanału websocketowego (można przy tym użyć frameworku Atmosphere lub skorzystać ze Springa tworząc kanał websocketowy z wykorzystaniem protokołu STOMP). Dla chętnych funkcjonalność komunikacji w obrębie grupy projektowej i możliwość przesyłania plików do wybranych użytkowników,
- aplikacja powinna używać mechanizmu rejestracji np. Logback z SLF4J.

### Kolejne zadania i technologie do rozważenia:
- Aplikacja reaktywna z użyciem Spring WebFlux (R2DBC - Reactive Relational Database Connectivity, Reactive Transactions, Backpressure, RSocket) lub korzystająca z wątków wirtualnych (z projektu Loom, wymagana Java 19 lub nowsza), 
- implementacja tablicy scrumowej,
- implementacja tablicy kanbanowej z ustawianymi limitami prac w każdej kolumnie, a także skumulowanego wykresu przepływu z nanoszonymi liniami trendu dla tempa przybywania i liczby elementów w systemie,
- aplikacja springowa z wykorzystaniem programowania funkcyjnego zastępującego większość adnotacji,
- GraphQL z użyciem Spring Boota. 

Trzeba będzie definiować m.in.:
 - tzw. Input czyli prostą klasę (POJO) dla przyjmowania danych z edycji w GraphQL,
 - QueryResolver, klasę obsługująca zapytania w GraphQL,
 - MutationResolver, klasę obsługująca modyfikacje w GraphQL,
 - plik schema dla GraphQL, opisujący strukturę bazy danych i dostępne metody,
- Elasticsearch w Spring Boot,
- SOAP (ang. Simple Object Access Protocol) - usługa opisywana przez udostępniany plik WSDL (nazwa operacji, jej dane wejściowe, ich typ itp.) zabezpieczona za pomocą SAML-a (ang. Security Assertion Markup Language),
- mechanizm bazodanowych triggerów do automatycznego archiwizowania zmian projektu i jego zadań,
- utworzenie Springowej aplikacji natywnej przy użyciu kompilatora GraalVM.


---
### Nadawanie statusu administratora
0. Dodajcie sobie studenta przez rejestrację na frontend
1. Wchodzicie w pgAdmin, co zainstalowaliście w ramach postgresql
2. Wpisujecie hasło (politechnika)?
3. Rozwijacie Servers -> postgresql -> projekty -> schemas -> table -> student (dam zrzut poniżej)
4. Prawy na student, scripts i select
5. Otworzy Wam się okienko Query, wciskacie ten trójkąt execute (lub f5)
6. wyświetli Wam się lista studentów - na końcu jest kolumna "role" zmiencie sobie na ADMIN i zapiszcie (F6 lub ikonka save data changes)
