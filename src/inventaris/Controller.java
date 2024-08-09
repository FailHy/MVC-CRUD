/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventaris;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Controller {
    
    private final MainView view;
    private final DatabaseModel model;
    
    public Controller(MainView view, DatabaseModel model) {
        this.view = view;
        this.model = model;
        
        // Mengambil data pertama kali saat aplikasi dijalankan
        view.ambildatapertamakali();
        
        // Menambahkan action listener untuk tombol-tombol di view
        view.setTambahListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahData();
            }
        });
        
        view.setUpdateListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });
        
        view.setHapusListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusData();
            }
        });
        
        view.setBersihkanListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
               kosongkanInput();
        }
    });

    }
    
    private void tambahData() {
        int nomorbuku = Integer.parseInt((String) view.getBoxNomorBuku());
        String judulbuku = (String) view.getBoxJudulBuku();
        String pengarang = (String) view.getBoxPengarang();
        int tahunterbit = Integer.parseInt((String) view.getBoxTahunTerbit());

        // Memeriksa apakah data sudah ada sebelum menambahkan data
        if (!model.cekData(nomorbuku)) {
            Object[] datanya = {nomorbuku, judulbuku, pengarang, tahunterbit};
            model.tambahdata(nomorbuku, judulbuku, pengarang, tahunterbit);
            view.kosongkantabel();
            view.masukinketabel(datanya);
        } else {
            view.datasudahada(nomorbuku);
        }
    }
    
    private void updateData() {
        int nomorbuku = Integer.parseInt((String) view.getBoxNomorBuku());
        String judulbuku = (String) view.getBoxJudulBuku();
        String pengarang = (String) view.getBoxPengarang();
        int tahunterbit = Integer.parseInt((String) view.getBoxTahunTerbit());

        // Memeriksa apakah data sudah ada sebelum melakukan update
        if (model.cekData(nomorbuku)) {
            model.updatedata(nomorbuku, judulbuku, pengarang, tahunterbit);
            view.kosongkantabel();
            view.ambildatapertamakali();
        } else {
            JOptionPane.showMessageDialog(null, "Data dengan Nomor Buku " + nomorbuku + " tidak ditemukan di database.");
        }
    }
    
    private void hapusData() {
        int nomorbuku = Integer.parseInt((String) view.getBoxHapusData());

        model.hapusdata(nomorbuku);
        view.kosongkantabel();
        view.ambildatapertamakali();
    }
    
    private void kosongkanInput() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
