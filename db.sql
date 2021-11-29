CREATE TABLE daftar_menu (
    id_daftar_menu  SERIAL NOT NULL,
    nama            VARCHAR(50) NOT NULL,
    harga           INTEGER NOT NULL,
    kategori        VARCHAR(10) NOT NULL
);
ALTER TABLE daftar_menu ADD CONSTRAINT daftar_menu_pk PRIMARY KEY ( id_daftar_menu );

CREATE TABLE pengguna (
    id_pengguna         SERIAL NOT NULL,
    nama                VARCHAR(50) NOT NULL,
    username            VARCHAR(50) NOT NULL unique,
    password            VARCHAR(50) NOT NULL,
	tingkat_id_tingkat  INTEGER NOT NULL
);
ALTER TABLE pengguna ADD CONSTRAINT pengguna_pk PRIMARY KEY ( id_pengguna );

CREATE TABLE pesanan (
    id_pesanan                  SERIAL NOT NULL,
    transaksi_id_transaksi      INTEGER NOT NULL,
	daftar_menu_id_daftar_menu  INTEGER NOT NULL
);
ALTER TABLE pesanan ADD CONSTRAINT pesanan_pk PRIMARY KEY ( id_pesanan );

CREATE TABLE tingkat (
    id_tingkat  SERIAL NOT NULL,
    hak_akses   VARCHAR(20) NOT NULL
);
ALTER TABLE tingkat ADD CONSTRAINT tingkat_pk PRIMARY KEY ( id_tingkat );

CREATE TABLE transaksi (
    id_transaksi          SERIAL NOT NULL,
    nama_pelanggan        VARCHAR(50) NOT NULL,
    tanggal_pembayaran    TIMESTAMP NOT NULL DEFAULT now(),
    dibayarkan            INTEGER NOT NULL,
	pengguna_id_pengguna  INTEGER NOT NULL
);
ALTER TABLE transaksi ADD CONSTRAINT transaksi_pk PRIMARY KEY ( id_transaksi );

ALTER TABLE pengguna
    ADD CONSTRAINT pengguna_tingkat_fk FOREIGN KEY ( tingkat_id_tingkat )
        REFERENCES tingkat ( id_tingkat );

ALTER TABLE pesanan
    ADD CONSTRAINT pesanan_daftar_menu_fk FOREIGN KEY ( daftar_menu_id_daftar_menu )
        REFERENCES daftar_menu ( id_daftar_menu );

ALTER TABLE pesanan
    ADD CONSTRAINT pesanan_transaksi_fk FOREIGN KEY ( transaksi_id_transaksi )
        REFERENCES transaksi ( id_transaksi );

ALTER TABLE transaksi
    ADD CONSTRAINT transaksi_pengguna_fk FOREIGN KEY ( pengguna_id_pengguna )
        REFERENCES pengguna ( id_pengguna );


insert into tingkat(hak_akses) values ('admin'), ('karyawan')
