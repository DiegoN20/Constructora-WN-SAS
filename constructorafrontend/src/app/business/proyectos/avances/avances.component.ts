import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AvancesService } from '../../../core/services/avances.service';
import { ActivatedRoute } from '@angular/router';
import { NewDialogComponent } from '../../../dialogs/dialogs-avances/new-dialog/new-dialog.component';
import { EditDialogComponent } from '../../../dialogs/dialogs-avances/edit-dialog/edit-dialog.component';
import { DeleteDialogComponent } from '../../../dialogs/dialogs-avances/delete-dialog/delete-dialog.component';
import { ViewDialogComponent } from '../../../dialogs/dialogs-avances/view-dialog/view-dialog.component';

@Component({
  selector: 'app-avances',
  imports: [CommonModule, MatDialogModule],
  templateUrl: './avances.component.html',
  styleUrl: './avances.component.css'
})
export class AvancesComponent implements OnInit{
  
  avances: any[] = [];
  currentPage: number = 1;
  pageSize: number = 10; // Número de Asignaciones por página
  paginatedAvances: any[] = [];
  Math = Math;
  proyectoId!: number;

  constructor(private avancesService: AvancesService,
    public dialog: MatDialog,
    private route: ActivatedRoute){}
  
  openNewDialog(): void {
    const dialogRef = this.dialog.open(NewDialogComponent,{
      data: { proyectoId: this.proyectoId } // Define valores iniciales
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Avance creado:', result);
        this.avances.push(result);
        this.getAvances();
        this.getPaginatedAvances();
      }
    });
  }

  openEditDialog(avances: any): void {
    const dialogRef = this.dialog.open(EditDialogComponent, {
      data: { ...avances }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) { // Si el usuario confirma
        console.log('Avance Modificado:', result);
        this.getAvances();
        this.getPaginatedAvances();
      }
    });
  } 

  openDeleteDialog(avances: any): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: { ...avances } // Pasa la asignación al diálogo
    });  
    dialogRef.afterClosed().subscribe(result => {
      if (result) { // Si el usuario confirma
        console.log('Avance eliminado:', result);
        this.getAvances();
        this.getPaginatedAvances();
      }
    });
  } 

  openViewDialog(avances: any): void {
    const dialogRef = this.dialog.open(ViewDialogComponent, {
      data: { ...avances }
    });
  }
  
  ngOnInit(): void {
    this.proyectoId = +this.route.snapshot.paramMap.get('id')!;
    this.getAvances();
  }

  getPaginatedAvances(): void {
    if (this.avances.length > 0) {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = Math.min(startIndex + this.pageSize, this.avances.length); // Ajuste de límites
      this.paginatedAvances = this.avances.slice(startIndex, endIndex);
    } else {
      this.paginatedAvances = [];
    }
  }
  
  changePage(newPage: number): void {
    if (newPage > 0 && newPage <= Math.ceil(this.avances.length / this.pageSize)) {
      this.currentPage = newPage;
      console.log('Página actual:', this.currentPage); // Registra la página actual
      this.getPaginatedAvances();
      console.log('Avances paginadas:', this.paginatedAvances); // Verifica los datos
    }
  }

  getAvances(): void {
    this.avancesService.getAvance(this.proyectoId).subscribe(
      (data) => {
        this.avances = data;
        this.getPaginatedAvances();
      },
      (error) => {
        console.error('Error al obtener Asignaciones:', error);
      }
    );
  }

}
