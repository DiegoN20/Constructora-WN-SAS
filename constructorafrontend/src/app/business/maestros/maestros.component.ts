import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MaestrosService } from '../../core/services/maestros.service';
import { NewDialogComponent } from '../../dialogs/dialogs-maestros/new-dialog/new-dialog.component';
import { EditDialogComponent } from '../../dialogs/dialogs-maestros/edit-dialog/edit-dialog.component';
import { DeleteDialogComponent } from '../../dialogs/dialogs-maestros/delete-dialog/delete-dialog.component';
import { ViewDialogComponent } from '../../dialogs/dialogs-maestros/view-dialog/view-dialog.component';

@Component({
  selector: 'app-maestros',
  imports: [CommonModule, MatDialogModule],
  templateUrl: './maestros.component.html',
  styleUrl: './maestros.component.css'
})
export default class MaestrosComponent implements OnInit{
  maestros: any[] = [];
  currentPage: number = 1;
  pageSize: number = 10; // Número de maestros por página
  paginatedMaestros: any[] = [];
  Math = Math;

  constructor(private maestrosService: MaestrosService, public dialog: MatDialog){}

  openNewDialog(): void {
    const dialogRef = this.dialog.open(NewDialogComponent,{
      data: { } // Define valores iniciales
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Maestro creado:', result);
        this.maestros.push(result);
        this.getMaestros();
        this.getPaginatedMaestros();
      }
    });
  }

  openEditDialog(maestro: any): void {
    const dialogRef = this.dialog.open(EditDialogComponent, {
      data: { ...maestro }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) { // Si el usuario confirma
        console.log('Maestro Modificado:', result);
        this.getMaestros();
        this.getPaginatedMaestros();
      }
    });
  } 

  openDeleteDialog(maestro: any): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: { ...maestro } // Pasa el Maestro al diálogo
    });
    
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) { // Si el usuario confirma
        console.log('Maestro eliminado:', result);
        this.getMaestros();
        this.getPaginatedMaestros();
      }
    });
  } 

  openViewDialog(maestro: any): void {
    const dialogRef = this.dialog.open(ViewDialogComponent, {
      data: { ...maestro }
    });
  }

  ngOnInit(): void {
    this.getMaestros();
  }

  getPaginatedMaestros(): void {
    if (this.maestros.length > 0) {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = Math.min(startIndex + this.pageSize, this.maestros.length); // Ajuste de límites
      this.paginatedMaestros = this.maestros.slice(startIndex, endIndex);
    } else {
      this.paginatedMaestros = [];
    }
  }
  
  changePage(newPage: number): void {
    if (newPage > 0 && newPage <= Math.ceil(this.maestros.length / this.pageSize)) {
      this.currentPage = newPage;
      console.log('Página actual:', this.currentPage); // Registra la página actual
      this.getPaginatedMaestros();
      console.log('Maestros paginados:', this.paginatedMaestros); // Verifica los datos
    }
  }

  getMaestros(): void {
    this.maestrosService.getMaestros().subscribe(
      (data) => {
        this.maestros = data;
        this.getPaginatedMaestros();
      },
      (error) => {
        console.error('Error al obtener Maestros:', error);
      }
    );
  }
}
