import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ProyectosService } from '../../core/services/proyectos.service';
import { ViewDialogComponent } from '../../dialogs/dialogs-proyectos/view-dialog/view-dialog.component';
import { DeleteDialogComponent } from '../../dialogs/dialogs-proyectos/delete-dialog/delete-dialog.component';
import { EditDialogComponent } from '../../dialogs/dialogs-proyectos/edit-dialog/edit-dialog.component';
import { NewDialogComponent } from '../../dialogs/dialogs-proyectos/new-dialog/new-dialog.component';

@Component({
  selector: 'app-proyectos',
  imports: [CommonModule, MatDialogModule],
  templateUrl: './proyectos.component.html',
  styleUrl: './proyectos.component.css'
})
export default class ProyectosComponent implements OnInit{
  proyectos: any[] = [];
  currentPage: number = 1;
  pageSize: number = 10; // Número de Proyectos por página
  paginatedProyectos: any[] = [];
  Math = Math;

  constructor(private proyectosService: ProyectosService, public dialog: MatDialog){}

  openNewDialog(): void {
    const dialogRef = this.dialog.open(NewDialogComponent,{
      data: { } // Define valores iniciales
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Proyecto creado:', result);
        this.proyectos.push(result);
        this.getProyectos();
        this.getPaginatedProyectos();
      }
    });
  }

  openEditDialog(proyecto: any): void {
    const dialogRef = this.dialog.open(EditDialogComponent, {
      data: { ...proyecto }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) { // Si el usuario confirma
        console.log('Proyecto Modificado:', result);
        this.getProyectos();
        this.getPaginatedProyectos();
      }
    });
  } 

  openDeleteDialog(proyecto: any): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: { ...proyecto } // Pasa el Proyecto al diálogo
    });
    
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) { // Si el usuario confirma
        console.log('Proyecto eliminado:', result);
        this.getProyectos();
        this.getPaginatedProyectos();
      }
    });
  } 

  openViewDialog(proyecto: any): void {
    const dialogRef = this.dialog.open(ViewDialogComponent, {
      data: { ...proyecto }
    });
  }

  ngOnInit(): void {
    this.getProyectos();
  }

  getPaginatedProyectos(): void {
    if (this.proyectos.length > 0) {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = Math.min(startIndex + this.pageSize, this.proyectos.length); // Ajuste de límites
      this.paginatedProyectos = this.proyectos.slice(startIndex, endIndex);
    } else {
      this.paginatedProyectos = [];
    }
  }
  
  changePage(newPage: number): void {
    if (newPage > 0 && newPage <= Math.ceil(this.proyectos.length / this.pageSize)) {
      this.currentPage = newPage;
      console.log('Página actual:', this.currentPage); // Registra la página actual
      this.getPaginatedProyectos();
      console.log('Proyectos paginados:', this.paginatedProyectos); // Verifica los datos
    }
  }

  getProyectos(): void {
    this.proyectosService.getProyectos().subscribe(
      (data) => {
        this.proyectos = data;
        this.getPaginatedProyectos();
      },
      (error) => {
        console.error('Error al obtener Proyectos:', error);
      }
    );
  }
}
