<h1>Project Covid Tracker</h1>

Aplikasi ini merupakan aplikasi mobile untuk melacak jumlah pasien covid dan angka-angka kasus covid.
arsitektur aplikasi yang kami gunakan disini ada MVP (Model View Presenter)

<h2>Layout</h2>
<ul>
   <li><h3>Home</h3></li>
   Untuk layout Home kami lebih menggunakan relative layout sebagai layout utama kami. kami juga menggunakan linear dan constraint layout dalam menyusun cardview dan bottomsheet tersebut
   <li><h3>LookUp</h3></li>
Untuk layout look up kami menggunakan relative layout dan untuk masing - masing item tersebut ditampilkan menggunkan linear layout manager. Item yang ditampilkan menggunakan card view dan constraint layout
   <li><h3>Bottom sheet Hotline</h3></li>
Untuk layout bottom sheet kami menggunakan koordinator layout dan recyclerview untuk menampilkan semua data hotline
   <li><h3>About pop</h3></li>
   Layout yang digunakan adalah card view dan constraint layout
   <li><h3>Component loading</h3></li>
   Untuk setiap item yang digunakan masing-masing layout diatas memiliki komponen loadng yang hanya terlihat seperti sketsa data ketika mengambil/fetch data dari API 
   <li><h3>Splash screen</h3></li>
   Ketika membuka aplikasi tersebut terdapat layout pembuka dari aplikasi tersebut
</ul>

<h2>Fitur</h2>
<ul>
    <li><h3>Home</h3></li>
    Untuk home terdapat 3 card view dan 1 text view yang masing-masing mendapatkan data dari API <a href="https://api.kawalcorona.com/indonesia">https://api.kawalcorona.com/indonesia</a>

   <li><h3>LookUp</h3></li>
    untuk look up kami menampilkan masing-masing data cardview tersebut dengan mengambil data dari API <a href="https://api.kawalcorona.com/indonesia/provinsi/">https://api.kawalcorona.com/indonesia/provinsi/</a>
<li><h3>Bottom sheet Hotline</h3></li>
    untuk masing-masing data yang ditampilkan dalam bottom sheet tersebut kami menggunakan data dari API <a href="https://bncc-corona-versus.firebaseio.com/v1/hotlines.json">https://bncc-corona-versus.firebaseio.com/v1/hotlines.json</a>
 <li><h3>About pop up</h3></li>
  
</ul>

<h2>Foldering/Package</h2>
foldering ini terdapat pada com.example.byevirus untuk mengetahui dimana package-package yang sesuai
<ul>
    <li><h3>activity</h3></li>
    Logic untuk tampilan viewnya seperti binding data dari presenter akan diletakkan pada folder tersebut
    <li><h3>adapter</h3></li>
    Adapter yang digunakan oleh recyclerview akan diletakkan disini
    <li><h3>constants</h3></li>
    semua data yang tidak akan berubah akan dibuatkan class yang isinya companion object dan dapat dipanggil di class manapun
    <li><h3>contract</h3></li>
    karena kami menggunakan MVP terdapat contract dimana setiap view dan presenter memiliki fungsi yang pasti diimplementasikan
    <li><h3>entity</h3></li>
    disini akan diletakkan semua bentuk data class yang kami terima dan tampilkan
    <li><h3>fragment</h3></li>
    logika untuk menampilkan fragment dan cara kerjanya akan diletakkan disini
    <li><h3>model</h3></li>
    model merupakan salah satu bagian dari MVP dimana tugas model adalah mengambil data dari sumber data
    <li><h3>presenter</h3></li>
    disini adalah letak presenter dimana tugasnya untuk menghubungkan model dan view
    <li><h3>viewHolder</h3></li>
    disini adalah letak view holder yang digunakan oleh recycler view
</ul>

<h2>Tampilan</h2>
<ul>
     <li><h3>Home</h3></li>
   <li><h3>LookUp</h3></li>
    <img src="https://drive.google.com/uc?export=view&id=1z5IfA9US-y8yDJ8W2BurW_6G1fw7A3mu" />
 <li><h3>Bottom sheet Hotline</h3></li>
 <img src="https://drive.google.com/uc?export=view&id="1zPBlz2c7xbLg9PsBOZM0b2Vkc_FTFELh=w1366-h656-iv1" />
  <li><h3>About pop up</h3></li>
</ul>
