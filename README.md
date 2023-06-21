# Programowanie zwinne

https://trello.com/b/9RUZ5X2m/22032023
---
## Zadania do wykonania


### Do zrobienia na zaliczenie:
- ZROBIONE - nastepna strona i poprzednia strona
- ZROBIONE - dodawanie zadan do stronie listy projektow
- ZROBIONE - walidacja logowania i rejestracji
- ZROBIONE - walidacja w dodawaniu projektu, zadan i studentow
- dodawanie pliku - sciezka do bazy
- biblioteka springowa do chatu - napisać email, jeśli nie będzie działać i dostaniemy punkciki za walkę do końca
- ZROBIONE - przygotować coś do bazy danych - administrator, np.
- problem z sortowaniem wyniku po wyszukiwaniu - może utworzyć stronę z tylko wynikami, bez przeglądania stron?
### Realizacja powinna uwzględniać m.in.:
- [ ] zabezpieczenie danych i aplikacji przed niepożądanym dostępem, 
- [ ] protokół szyfrowania transmisji danych (w tym celu należy wygenerować certyfikat SSL np. za pomocą narzędzia keytool dostępnego w każdym JDK),
- [x] odrębne uprawnienia dla prowadzącego i studentów (dla nas administrator),
- [ ] testy jednostkowe i integracyjne (koniecznie serwisów i kontrolerów, zalecane JUnit5, Mockito i MockMVC),
- [x] pełną funkcjonalność systemu pozwalającą dodawać, modyfikować i usuwać dane projektów, zadań i studentów. Powinna istnieć możliwość stronicowania i wyszukiwania, opcjonalnie sortowania, danych projektów i studentów.
- [ ] możliwość przesyłania na serwer i pobierania plików przypisywanych do danego projektu lub zadania, 
- [ ] ogólnodostępny chat korzystający z dwukierunkowego kanału websocketowego (można przy tym użyć frameworku Atmosphere lub skorzystać ze Springa tworząc kanał websocketowy z wykorzystaniem protokołu STOMP). Dla chętnych funkcjonalność komunikacji w obrębie grupy projektowej i możliwość przesyłania plików do wybranych użytkowników,
- [x] aplikacja powinna używać mechanizmu rejestracji np. Logback z SLF4J.

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
### Nadawanie statusu administratora:
0. Dodajcie sobie studenta przez rejestrację na frontend
1. Wchodzicie w pgAdmin, co zainstalowaliście w ramach postgresql
2. Wpisujecie hasło (politechnika)?
3. Rozwijacie Servers -> postgresql -> projekty -> schemas -> table -> student (dam zrzut poniżej)
4. Prawy na student, scripts i select
5. Otworzy Wam się okienko Query, wciskacie ten trójkąt execute (lub f5)
6. wyświetli Wam się lista studentów - na końcu jest kolumna "role" zmiencie sobie na ADMIN i zapiszcie (F6 lub ikonka save data changes)

![](https://scontent-waw1-1.xx.fbcdn.net/v/t1.15752-9/343547797_5594167890683379_9020410397301631774_n.png?_nc_cat=109&ccb=1-7&_nc_sid=ae9488&_nc_ohc=Nitvk_etTWwAX9T23_d&_nc_ht=scontent-waw1-1.xx&oh=03_AdS8nNbXLapCqmt0cGdipTYGWiT2y1sDlImyYZ5RHPY_cg&oe=649D4231)
![](https://scontent-waw1-1.xx.fbcdn.net/v/t1.15752-9/345246725_1040713287204973_9183457215237163721_n.png?_nc_cat=101&ccb=1-7&_nc_sid=ae9488&_nc_ohc=C23zvrXD12sAX8XSwa7&_nc_ht=scontent-waw1-1.xx&oh=03_AdRiMu9hLRMjth2NDFfLNvhAtBRKg2rb4xbc-yByQqf2Hg&oe=649D56DD)
![](https://scontent-waw1-1.xx.fbcdn.net/v/t1.15752-9/343607650_821259006303520_1711665949002987788_n.png?_nc_cat=108&ccb=1-7&_nc_sid=ae9488&_nc_ohc=hR_rebUMA_wAX-B-Ksu&_nc_ht=scontent-waw1-1.xx&oh=03_AdSxMxeZj7PYJdsLi-f4ZTQXVbAjJ4sjrwi0QSMfsWCT4g&oe=649D5957)
