import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AsignacionesService } from '../../../core/services/asignaciones.service';
import { ActivatedRoute } from '@angular/router';
import { NewDialogComponent } from '../../../dialogs/dialogs-asignaciones/new-dialog/new-dialog.component';
import { EditDialogComponent } from '../../../dialogs/dialogs-asignaciones/edit-dialog/edit-dialog.component';
import { DeleteDialogComponent } from '../../../dialogs/dialogs-asignaciones/delete-dialog/delete-dialog.component';
import { ViewDialogComponent } from '../../../dialogs/dialogs-asignaciones/view-dialog/view-dialog.component';

@Component({
  selector: 'app-asignaciones',
  imports: [CommonModule, MatDialogModule],
  templateUrl: './asignaciones.component.html',
  styleUrl: './asignaciones.component.css'
})
export class AsignacionesComponent implements OnInit{
  asignaciones: any[] = [];
  currentPage: number = 1;
  pageSize: number = 10; // Número de Asignaciones por página
  paginatedAsignaciones: any[] = [];
  Math = Math;
  proyectoId!: number;

  constructor(private asignacionesService: AsignacionesService,
    public dialog: MatDialog,
    private route: ActivatedRoute){}

  openNewDialog(): void {
    const dialogRef = this.dialog.open(NewDialogComponent,{
      data: { proyectoId: this.proyectoId } // Define valores iniciales
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Asignación creada:', result);
        this.asignaciones.push(result);
        this.getAsignaciones();
        this.getPaginatedAsignaciones();
      }
    });
  }

  openEditDialog(asignaciones: any): void {
    const dialogRef = this.dialog.open(EditDialogComponent, {
      data: { ...asignaciones }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) { // Si el usuario confirma
        console.log('Asignación Modificada:', result);
        this.getAsignaciones();
        this.getPaginatedAsignaciones();
      }
    });
  } 

  openDeleteDialog(asignaciones: any): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: { ...asignaciones } // Pasa la asignación al diálogo
    });
    
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) { // Si el usuario confirma
        console.log('Asignación eliminada:', result);
        this.getAsignaciones();
        this.getPaginatedAsignaciones();
      }
    });
  } 

  openViewDialog(asignaciones: any): void {
    const dialogRef = this.dialog.open(ViewDialogComponent, {
      data: { ...asignaciones }
    });
  }

  ngOnInit(): void {
    this.proyectoId = +this.route.snapshot.paramMap.get('id')!;
    this.getAsignaciones();
  }

  getPaginatedAsignaciones(): void {
    if (this.asignaciones.length > 0) {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = Math.min(startIndex + this.pageSize, this.asignaciones.length); // Ajuste de límites
      this.paginatedAsignaciones = this.asignaciones.slice(startIndex, endIndex);
    } else {
      this.paginatedAsignaciones = [];
    }
  }
  
  changePage(newPage: number): void {
    if (newPage > 0 && newPage <= Math.ceil(this.asignaciones.length / this.pageSize)) {
      this.currentPage = newPage;
      console.log('Página actual:', this.currentPage); // Registra la página actual
      this.getPaginatedAsignaciones();
      console.log('Asignaciones paginadas:', this.paginatedAsignaciones); // Verifica los datos
    }
  }

  getAsignaciones(): void {
    this.asignacionesService.getAsignaciones(this.proyectoId).subscribe(
      (data) => {
        this.asignaciones = data;
        this.getPaginatedAsignaciones();
      },
      (error) => {
        console.error('Error al obtener Asignaciones:', error);
      }
    );
  }

}
