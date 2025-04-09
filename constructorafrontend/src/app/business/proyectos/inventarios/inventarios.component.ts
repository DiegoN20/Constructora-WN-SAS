import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { InventariosService } from '../../../core/services/inventarios.service';
import { ActivatedRoute } from '@angular/router';
import { ViewDialogComponent } from '../../../dialogs/dialogs-inventarios/view-dialog/view-dialog.component';
import { DeleteDialogComponent } from '../../../dialogs/dialogs-inventarios/delete-dialog/delete-dialog.component';
import { EditDialogComponent } from '../../../dialogs/dialogs-inventarios/edit-dialog/edit-dialog.component';
import { NewDialogComponent } from '../../../dialogs/dialogs-inventarios/new-dialog/new-dialog.component';

@Component({
  selector: 'app-inventarios',
  imports: [CommonModule, MatDialogModule],
  templateUrl: './inventarios.component.html',
  styleUrl: './inventarios.component.css'
})
export class InventariosComponent implements OnInit{
  inventarios: any[] = [];
  currentPage: number = 1;
  pageSize: number = 10; // Número de Asignaciones por página
  paginatedInventarios: any[] = [];
  Math = Math;
  proyectoId!: number;

  constructor(private inventariosService: InventariosService,
    public dialog: MatDialog,
    private route: ActivatedRoute){}

  openNewDialog(): void {
    const dialogRef = this.dialog.open(NewDialogComponent,{
      data: { proyectoId: this.proyectoId } // Define valores iniciales
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Inventario creado:', result);
        this.inventarios.push(result);
        this.getInventarios();
        this.getPaginatedInventarios();
      }
    });
  }

  openEditDialog(inventarios: any): void {
    const dialogRef = this.dialog.open(EditDialogComponent, {
      data: { ...inventarios }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) { // Si el usuario confirma
        console.log('Inventario Modificado:', result);
        this.getInventarios();
        this.getPaginatedInventarios();
      }
    });
  } 

  openDeleteDialog(inventarios: any): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: { ...inventarios } // Pasa la asignación al diálogo
    });
    
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) { // Si el usuario confirma
        console.log('Inventario eliminado:', result);
        this.getInventarios();
        this.getPaginatedInventarios();
      }
    });
  } 

  openViewDialog(inventarios: any): void {
    const dialogRef = this.dialog.open(ViewDialogComponent, {
      data: { ...inventarios }
    });
  }
  
  
  ngOnInit(): void {
    this.proyectoId = +this.route.snapshot.paramMap.get('id')!;
    this.getInventarios();
  }

  getPaginatedInventarios(): void {
    if (this.inventarios.length > 0) {
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = Math.min(startIndex + this.pageSize, this.inventarios.length); // Ajuste de límites
      this.paginatedInventarios = this.inventarios.slice(startIndex, endIndex);
    } else {
      this.paginatedInventarios = [];
    }
  }
  
  changePage(newPage: number): void {
    if (newPage > 0 && newPage <= Math.ceil(this.inventarios.length / this.pageSize)) {
      this.currentPage = newPage;
      console.log('Página actual:', this.currentPage); // Registra la página actual
      this.getPaginatedInventarios();
      console.log('Inventarios paginados:', this.paginatedInventarios); // Verifica los datos
    }
  }

  getInventarios(): void {
    this.inventariosService.getInventario(this.proyectoId).subscribe(
      (data) => {
        this.inventarios = data;
        this.getPaginatedInventarios();
      },
      (error) => {
        console.error('Error al obtener Inventarios:', error);
      }
    );
  }

}
