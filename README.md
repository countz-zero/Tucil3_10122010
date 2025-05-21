# Solver Permainan Rush Hour
### Dibuat oleh : Dzubyan Ilman Ramadhan (10122010)
Rush Hour adalah permainan yang bertujuan untuk menegluarkan piece utama dengan menggeser piece lain.

Program ini adalah solver untuk puzzle Rush Hour dengan menggunakan algoritma pathfinding berikut : Greedy Best First Search, Uniform Cost Search, dan A* dalam bahasa Java

Pengguna akan dapat memilih algoritma yang ingin digunakan untuk mencari solusi dari sebuah konfigurasi papan permainan Rush Hour.

## Requirement 
* Java 23 atau lebih tinggi
* Maven Wrapper disediakan untuk menjalankan Maven sehingga tidak perlu menginstall Maven, namun untuk info tambahan, kita menggunakan Apache Maven 3.9.9


## Cara mengkompilasi dan Menjalankan program
1. Download source file
2. Di Terminal Command Prompt, pergi ke lokasi folder `rush-hour-solver` dengan perintah `cd`
3. Jalankan `mvn compile`

### ATAU
1. Jalankan `mvn clean package`
2. Jalankan `java -jar target/rushhoursolver-1.0-SNAPSHOT.jar`
   
## Cara Menggunakan Program 
1. Buat sebuah konfigurasi papan Rush Hour yang diinginkan di direktori `main\resources` dengan format berikut :
   
   A B
   
   N
   
   konfigurasi_papan

   dimana A adalah tinggi papan, B adalah lebar papan, dan N adalah banyak blok selain mobil target lain.
   Contoh konfigurasi papan :
   
   AAB..F
   
   ..BCDF
   
   GPPCDFK
   
   GH.III
   
   GHJ...
   
   LLJMM.

   dimana K menyimbolkan tempat keluar untuk mobil utama P yang ingin dikeluarkan.
3. Namakan file txt tersebut `input.txt` 
   
4. Kompilasi dan jalankan program `App.java`
5. Masukkan nama file input dengan ekstensi `.txt`
6. Masukkan kode metode heuristics yang diinginkan sesuai yang tertera di terminal. (`G` untuk Greedy Best First Search, `U` untuk Uniform Cost Search, `A` untuk A*)
7. Output akan dihasilkan di terminal dan file txt `output.txt` di folder `test` dengan waktu untuk menyelesaikan permainan serta format penyelesaian yang mudah dipahami.


## Troubleshooting

> Folder test kok isinya bukan txt?

Anda mungkin tidak sengaja membuka folder `test` untuk keperluan testing. Folder `test` yang benar berada di luar, bersama folder `bin`, `src`, dan lainnya.

> `mvn compile` tidak bisa

Update Java Anda menjadi yang terbaru.

> Masih tidak bisa :(

Lakukan step ini untuk kompilasi manual: 
1. Buka terminal command prompt
2. Jalankan `cd src/main/java/dvp`
3. Jalankan `javac App.java`
4. Jalankan `java App`

> Kok ada error input? Padahal udah bener :(

Pastikan baris atau kolom yang mengandung K harus berisi whitespace

Contoh untuk : 

```
AA.
PP.K
BB.
```

Pastikan ada spasi setelah `AA.` dan `BB.`



   




