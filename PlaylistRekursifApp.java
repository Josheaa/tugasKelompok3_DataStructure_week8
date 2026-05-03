package kuliah.tugas_kelompok.lec.t3;

/*
Tugas Kelompok 3 - Week 8
Nama Kelompok:
2902743763 - BATARA HADI PRAYITNO
2902742861 - DIRK EIBEL LAMBANG
2902692844 - JERI IRAWAN
2902703293 - JOSUA EMANUELLE ARSETO
2602211871 - MATIAS NDAWA ATA BARA
*/

import java.util.Scanner;

// CLASS LAGU
class Lagu {
    String judul;
    String artis;
    double durasi;

    Lagu(String judul, String artis, double durasi) {
        this.judul = judul;
        this.artis = artis;
        this.durasi = durasi;
    }

    public void tampilkanInfo() {
        System.out.println(judul + " - " + artis + " (" + durasi + " menit)");
    }
}

// CLASS REKURSIF
class PlaylistRekursif {

    /*
    Fungsi: totalDurasi
    Tujuan: menghitung total durasi semua lagu

    Base Case:
    n == 0 → return 0

    Recursive Case:
    durasi[n-1] + totalDurasi(n-1)

    Kompleksitas:
    O(n)
    */
    static double totalDurasi(Lagu[] list, int n) {
        if (n == 0)
            return 0;

        if (list[n - 1] == null)
            return totalDurasi(list, n - 1);

        return list[n - 1].durasi + totalDurasi(list, n - 1);
    }

    /*
    Fungsi: tampilkanMundur
    Tujuan: menampilkan lagu dari belakang

    Base Case:
    index < 0 → stop

    Recursive Case:
    print → index-1

    Kompleksitas:
    O(n)
    */
    static void tampilkanMundur(Lagu[] list, int index) {
        if (index < 0)
            return;

        if (list[index] != null) {
            System.out.println((index + 1) + ". " + list[index].judul);
        }

        tampilkanMundur(list, index - 1);
    }

    /*
    Fungsi: cariDurasiTerpanjang
    Tujuan: mencari durasi terbesar

    Base Case:
    index == 0

    Recursive Case:
    bandingkan dengan sebelumnya

    Kompleksitas:
    O(n)
    */
    static double cariDurasiTerpanjang(Lagu[] list, int index) {

        if (index == 0) {
            if (list[0] != null)
                return list[0].durasi;
            else
                return 0;
        }

        double max = cariDurasiTerpanjang(list, index - 1);

        if (list[index] != null && list[index].durasi > max)
            return list[index].durasi;

        return max;
    }
}

// MAIN CLASS
public class PlaylistRekursifApp {

    public static void main(String[] args) {

        Lagu[] playlist = new Lagu[10];
        Scanner input = new Scanner(System.in);

        boolean jalan = true;

        while (jalan) {

            System.out.println("\n=== MENU PLAYLIST MUSIK ===");
            System.out.println("1. Tampilkan semua lagu");
            System.out.println("2. Tambah lagu");
            System.out.println("3. Hapus lagu");
            System.out.println("4. Cari lagu");
            System.out.println("5. Urutkan durasi");
            System.out.println("6. Analisis Rekursif");
            System.out.println("7. Keluar");
            System.out.print("Pilih: ");

            String pilih = input.nextLine();

            switch (pilih) {

                case "1":
                    tampilkanSemua(playlist);
                    break;

                case "2":
                    tambah(playlist, input);
                    break;

                case "3":
                    hapus(playlist, input);
                    break;

                case "4":
                    cari(playlist, input);
                    break;

                case "5":
                    sortDurasi(playlist);
                    break;

                case "6":
                    analisisRekursif(playlist);
                    break;

                case "7":
                    jalan = false;
                    break;

                default:
                    System.out.println("Salah input.");
            }
        }

        input.close();
    }

    // ANALISIS UTAMA (OUTPUT TUGAS)
    static void analisisRekursif(Lagu[] playlist) {

        int jumlah = 0;
        for (Lagu l : playlist)
            if (l != null) jumlah++;

        if (jumlah == 0) {
            System.out.println("Playlist kosong.");
            return;
        }

        System.out.println("\n=== ANALISIS REKURSIF PLAYLIST ===");
        System.out.println("Jumlah lagu : " + jumlah);

        // total durasi
        long s1 = System.nanoTime();
        double total = PlaylistRekursif.totalDurasi(playlist, playlist.length);
        long e1 = System.nanoTime();

        System.out.println("Total durasi : " + total + " menit");

        // durasi terpanjang
        long s2 = System.nanoTime();
        double max = PlaylistRekursif.cariDurasiTerpanjang(playlist, playlist.length - 1);
        long e2 = System.nanoTime();

        String judulMax = "", artisMax = "";
        for (Lagu l : playlist) {
            if (l != null && l.durasi == max) {
                judulMax = l.judul;
                artisMax = l.artis;
                break;
            }
        }

        System.out.println("Lagu terpanjang : \"" + judulMax + "\" - " + artisMax + " (" + max + " menit)");

        // tampil mundur
        System.out.println("\nDaftar lagu (terbalik):");

        long s3 = System.nanoTime();
        PlaylistRekursif.tampilkanMundur(playlist, playlist.length - 1);
        long e3 = System.nanoTime();

        // waktu ms
        System.out.println("\nExecution Time:");
        System.out.println("totalDurasi: " + (e1 - s1) / 1_000_000 + " ms");
        System.out.println("tampilkanMundur: " + (e3 - s3) / 1_000_000 + " ms");
        System.out.println("cariDurasiTerpanjang: " + (e2 - s2) / 1_000_000 + " ms");
    }

    // FITUR TAMBAHAN (ASAL)
    static void tampilkanSemua(Lagu[] p) {
        for (int i = 0; i < p.length; i++) {
            if (p[i] != null)
                System.out.println((i + 1) + ". " + p[i].judul);
        }
    }

    static void tambah(Lagu[] p, Scanner in) {
        for (int i = 0; i < p.length; i++) {
            if (p[i] == null) {
                System.out.print("Judul: ");
                String j = in.nextLine();
                System.out.print("Artis: ");
                String a = in.nextLine();
                System.out.print("Durasi: ");
                double d = Double.parseDouble(in.nextLine());

                p[i] = new Lagu(j, a, d);
                return;
            }
        }
        System.out.println("Penuh.");
    }

    static void hapus(Lagu[] p, Scanner in) {
        System.out.print("Judul: ");
        String j = in.nextLine();

        for (int i = 0; i < p.length; i++) {
            if (p[i] != null && p[i].judul.equalsIgnoreCase(j)) {
                for (int k = i; k < p.length - 1; k++) {
                    p[k] = p[k + 1];
                }
                p[p.length - 1] = null;
                return;
            }
        }
    }

    static void cari(Lagu[] p, Scanner in) {
        System.out.print("Cari: ");
        String c = in.nextLine();

        for (Lagu l : p) {
            if (l != null && l.judul.toLowerCase().contains(c.toLowerCase())) {
                l.tampilkanInfo();
            }
        }
    }

    static void sortDurasi(Lagu[] p) {
        for (int i = 0; i < p.length - 1; i++) {
            for (int j = 0; j < p.length - i - 1; j++) {
                if (p[j] != null && p[j + 1] != null &&
                        p[j].durasi > p[j + 1].durasi) {

                    Lagu temp = p[j];
                    p[j] = p[j + 1];
                    p[j + 1] = temp;
                }
            }
        }
        System.out.println("Sorted.");
    }
}