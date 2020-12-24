# BulkAddPaket
Office Project untuk bulk add paket

Cara add paket bulk:
1. Siapkan data.
2. masukan ke table BOM di skema prepaid tessadb, dng contoh script berikut:
  INSERT INTO "PREPAID"."BOM"("OPERATOR","STB_ID","OPERATION",  "PAKETNAME", "PAKETDUR" ) VALUES ('DAUS','69590898XX','ADDPAKET', 'NUSA FTA', '10 YEAR');
3. jalankan job nya di sumaengine:
  /home/suma/BulkManagePaket
  runBMP.sh
  
  
