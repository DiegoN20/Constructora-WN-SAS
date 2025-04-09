import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { InsumosService } from '../../core/services/insumos.service';
import { NewDialogComponent } from '../../dialogs/dialogs-insumos/new-dialog/new-dialog.component';
import { EditDialogComponent } from '../../dialogs/dialogs-insumos/edit-dialog/edit-dialog.component';
import { DeleteDialogComponent } from '../../dialogs/dialogs-insumos/delete-dialog/delete-dialog.component';
import { ViewDialogComponent } from '../../dialogs/dialogs-insumos/view-dialog/view-dialog.component';

@Component({
  selector: 'app-insumos',
  imports: [CommonModule, MatDialogModule],
  templateUrl: './insumos.component.html',
  styleUrl: './insumos.component.css'
})
export default class InsumosComponent implements OnInit{
  insumos: any[] = [];
  currentPage: number = 1;
  pageSize: number = 10; // Número de insumos por página
  paginatedInsumos: any[] = [];
  Math = Math;

  constructor(private insumosService: InsumosService, public dialog: MatDialog){}

  ngOnInit(): void {
    this.getInsumos();
  }

  openNewDialog(): void {
    const dialogRef = this.dialog.open(NewDialogComponent,{
      data: { } // Define valores iniciales
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Insumo creado:', result);
        this.insumos.push(result);
        this.getInsumos();
        this.getPaginatedInsumos();
      }
    });
  }

  openEditDialog(insumo: any): void {
    const dialogRef = this.dialog.open(EditDialogComponent, {
      data: { ...insumo }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) { // Si el usuario confirma
        console.log('Insumo Modificado:', result);
        this.getInsumos();
        this.getPaginatedInsumos();
      }
    });
  }

  openDeleteDialog(insumo: any): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: { ...insumo } // Pasa el Insumo al diálogo
    });
    
  
    dialogRef.afterClosed().subscribe(result => {
      
      if (result) { // Si el usuario confirma
        console.log('Insumo eliminado:', result);
        this.getInsumos();
        this.getPaginatedInsumos();
      }
    });
  }

  openViewDialog(insumo: any): void {
    const dialogRef = this.dialog.open(ViewDialogComponent, {
      data: { ...insumo }
    });
  }

  getPaginatedInsumos(): void {
    if (this.insumos.length > 0) {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = Math.min(startIndex + this.pageSize, this.insumos.length); // Ajuste de límites
      this.paginatedInsumos = this.insumos.slice(startIndex, endIndex);
    } else {
      this.paginatedInsumos = [];
    }
  }

  changePage(newPage: number): void {
    if (newPage > 0 && newPage <= Math.ceil(this.insumos.length / this.pageSize)) {
      this.currentPage = newPage;
      console.log('Página actual:', this.currentPage); // Registra la página actual
      this.getPaginatedInsumos();
      console.log('Insumos paginados:', this.paginatedInsumos); // Verifica los datos
    }
  }

  getInsumos(): void {
    this.insumosService.getInsumos().subscribe(
      (data) => {
        this.insumos = data;
        this.getPaginatedInsumos();
      },
      (error) => {
        console.error('Error al obtener Insumos:', error);
      }
    );
  }
}
