CREATE DATABASE IF NOT EXISTS perpustakaan;
USE perpustakaan;

CREATE TABLE IF NOT EXISTS buku (
    id INT AUTO_INCREMENT PRIMARY KEY,
    judul VARCHAR(255) NOT NULL,
    pengarang VARCHAR(255) NOT NULL,
    penerbit VARCHAR(255),
    tahun INT
);

CREATE TABLE IF NOT EXISTS anggota (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(255) NOT NULL,
    alamat TEXT,
    telepon VARCHAR(20)
);

-- Insert some sample data
INSERT INTO buku (judul, pengarang, penerbit, tahun) VALUES 
('Belajar Java Dasar', 'Budi Raharjo', 'Informatika', 2021),
('Pemrograman Swing', 'Andi Sunyoto', 'Andi Offset', 2022);

INSERT INTO anggota (nama, alamat, telepon) VALUES 
('John Doe', 'Jl. Merdeka No. 10', '08123456789'),
('Jane Smith', 'Jl. Sudirman No. 5', '08987654321');
