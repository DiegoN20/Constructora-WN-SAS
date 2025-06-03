import { Component, OnInit } from '@angular/core';
import { ProveedoresService } from '../../core/services/proveedores.service';
import { CommonModule } from '@angular/common';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { EditDialogComponent } from '../../dialogs/dialogs-proveedores/edit-dialog/edit-dialog.component';
import { DeleteDialogComponent } from '../../dialogs/dialogs-proveedores/delete-dialog/delete-dialog.component';
import { ViewDialogComponent } from '../../dialogs/dialogs-proveedores/view-dialog/view-dialog.component';
import { NewDialogComponent } from '../../dialogs/dialogs-proveedores/new-dialog/new-dialog.component';

@Component({
  selector: 'app-proveedores',
  imports: [CommonModule, MatDialogModule],
  templateUrl: './proveedores.component.html',
  styleUrl: './proveedores.component.css'
})
export default class ProveedoresComponent implements OnInit {
  proveedores: any[] = [];
  currentPage: number = 1;
  pageSize: number = 10; // Número de proveedores por página
  paginatedProveedores: any[] = [];
  Math = Math;

  constructor(private proveedoresService: ProveedoresService, public dialog: MatDialog) {}

  openNewDialog(): void {
    const dialogRef = this.dialog.open(NewDialogComponent,{
      data: { nombreProveedor: '', tipoServicio: '', correo: '', telefono: null } // Define valores iniciales
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Proveedor creado:', result);
        this.proveedores.push(result);
        this.getProveedores();
        this.getPaginatedProveedores();
      }
    });
  }

  openEditDialog(proveedor: any): void {
    const dialogRef = this.dialog.open(EditDialogComponent, {
      data: { ...proveedor }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) { // Si el usuario confirma
        console.log('Proveedor Modificado:', result);
        this.getProveedores();
        this.getPaginatedProveedores();
      }
    });
  } 

  openDeleteDialog(proveedor: any): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: { ...proveedor } // Pasa el proveedor al diálogo
    });
    
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) { // Si el usuario confirma
        console.log('Proveedor eliminado:', result);
        this.getProveedores();
        this.getPaginatedProveedores();
      }
    });
  } 

  openViewDialog(proveedor: any): void {
    const dialogRef = this.dialog.open(ViewDialogComponent, {
      data: { ...proveedor }
    });
  }

  ngOnInit(): void {
    this.getProveedores();
  }

  getPaginatedProveedores(): void {
    if (this.proveedores.length > 0) {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = Math.min(startIndex + this.pageSize, this.proveedores.length); // Ajuste de límites
      this.paginatedProveedores = this.proveedores.slice(startIndex, endIndex);
    } else {
      this.paginatedProveedores = [];
    }
  }
  
  changePage(newPage: number): void {
    if (newPage > 0 && newPage <= Math.ceil(this.proveedores.length / this.pageSize)) {
      this.currentPage = newPage;
      console.log('Página actual:', this.currentPage); // Registra la página actual
      this.getPaginatedProveedores();
      console.log('Proveedores paginados:', this.paginatedProveedores); // Verifica los datos
    }
  }

  getProveedores(): void {
    this.proveedoresService.getProveedores().subscribe(
      (data) => {
        this.proveedores = data;
        this.getPaginatedProveedores();
      },
      (error) => {
        console.error('Error al obtener proveedores:', error);
      }
    );
  }
}