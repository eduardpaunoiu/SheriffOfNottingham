
Clasele au fost grupate in pachete, in functie de functionalitatea implementata in fiecare clasa(clasele cu functionalitate similara sau inrudite au fost grupate in acelasi pachet)

S-a folosit si o clasa Utils care contine metode statice, care implementeaza functionalitati generale (cum ar fi calcularea frecventei fiecarui numar intr-un sir de numere sau diferite prelucrari de Liste si map-uri)

Pentru stocarea datelor despre un jucator(lista de carti, numarul de puncte, daca jucatorul este sau nu serif la un moment dat, strategia folosita la subrunda curenta, lista de carti selectate(sacul) etc...)
s-au creat clase dedicate de tip DTO (data transfer objects) grupate in pachetul dto

La executarea clasei Main, se apeleaza metoda startGame din clasa PlayGame care apeleaza apoi metodele corespunzatoare celor patru etape ale unui joc:

- initializarea jucatorilor cu datele si cartile corespunzatoare unei noi runde 
- aplicarea startegiei pentru fiecare jucator. Functionalitatea pentru aplicarea celor trei strategii este implementata in clase separate, grupate in pachetul game

- dupa aplicarea strategiei specifice fiecarui jucator, se apeleaza metodele din clasa ApplyInspection pentru a realiza etapa de inspectie a bunurilor. Tot aiic se face si actualizarea numarului de monede pentru fiecare jucator in functie de penalizarea sau castigul obtinute in urma inspectiei.

- Urmeaza apelul metodelor care calculeaza punctajul si bonusurile castigate la sfarsitul unei subrunde si actualizarea listei jucatorilor cu aceste date.

- Pentru a realiza sortarea facila a diferitelor liste de obiecte in functie de diverse criterii, cu ar fi sortarea listei de carti cu aceeasi frecventa dupa frecventa si profit in ordine descrescatoare sau sortarea listei de jucatori dupa puncaj pentru obtinerea clasamentului final, s-au folosit clase care implementeaza interfata Comparator din java









