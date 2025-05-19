# rush-hour-solver
### Dibuat oleh : Dzubyan Ilman Ramadhan (10122010)

Rush Hour adalah permainan yang bertujuan untuk menegluarkan piece utama dengan menggeser piece lain.

Program ini adalah solver untuk puzzle Rush Hour dengan menggunakan algoritma pathfinding berikut : Greedy Best First Search, Uniform Cost Search, dan A*

Pengguna akan dapat memilih algoritma yang ingin digunakan untuk mencari solusi dari sebuah konfigurasi papan permainan Rush Hour.

Requirement : 
* Java 23 atau lebih tinggi
* Maven Wrapper disediakan untuk menjalankan Maven sehingga tidak perlu menginstall Maven, namun untuk info tambahan, kita menggunakan Apache Maven 3.9.9


Cara mengkompilasi dan menjalankan program : 
1. Download source file
2. Di Terminal Command Prompt, pergi ke lokasi folder `rush-hour-solver` dengan perintah `cd`
{Ini entar diganti}
4. Jalankan `{untuk compile}`
5. Jalankan `{untuk execute}`


Cara Menggunakan Program : 
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
   
4. Jalankan program dengan `mvn {sesuatu}`
5. Masukkan kode metode heuristics yang diinginkan sesuai yang tertera di terminal. (`G` untuk Greedy Best First Search, `U` untuk Uniform Cost Search, `A` untuk A*)
6. Output akan dihasilkan di terminal dengan waktu untuk menyelesaikan permainan serta format penyelesaian yang mudah dipahami.


   
   




