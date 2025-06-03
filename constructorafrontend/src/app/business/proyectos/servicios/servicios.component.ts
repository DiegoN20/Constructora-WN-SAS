import { Component, OnInit } from '@angular/core';
import { ServiciosService } from '../../../core/services/servicios.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { NewDialogComponent } from '../../../dialogs/dialogs-servicios/new-dialog/new-dialog.component';
import { EditDialogComponent } from '../../../dialogs/dialogs-servicios/edit-dialog/edit-dialog.component';
import { DeleteDialogComponent } from '../../../dialogs/dialogs-servicios/delete-dialog/delete-dialog.component';
import { ViewDialogComponent } from '../../../dialogs/dialogs-servicios/view-dialog/view-dialog.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-servicios',
  imports: [CommonModule, MatDialogModule],
  templateUrl: './servicios.component.html',
  styleUrl: './servicios.component.css'
})
export class ServiciosComponent implements OnInit{
  servicios: any[] = [];
  currentPage: number = 1;
  pageSize: number = 10; // Número de Servicios por página
  paginatedServicios: any[] = [];
  Math = Math;
  proyectoId!: number;

  constructor(private serviciosService: ServiciosService,
      public dialog: MatDialog,
      private route: ActivatedRoute){}
  
  openNewDialog(): void {
    const dialogRef = this.dialog.open(NewDialogComponent,{
      data: { proyectoId: this.proyectoId } // Define valores iniciales
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('Asignación creada:', result);
        this.servicios.push(result);
        this.getServicios();
        this.getPaginatedServicios();
      }
    });
  }

  openEditDialog(servicios: any): void {
    const dialogRef = this.dialog.open(EditDialogComponent, {
      data: { ...servicios }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) { // Si el usuario confirma
        console.log('Servicio Modificado:', result);
        this.getServicios();
        this.getPaginatedServicios();
      }
    });
  }

  openDeleteDialog(servicios: any): void {
      const dialogRef = this.dialog.open(DeleteDialogComponent, {
        data: { ...servicios } // Pasa la asignación al diálogo
      });
      
    
      dialogRef.afterClosed().subscribe(result => {
        if (result) { // Si el usuario confirma
          console.log('Servicio eliminado:', result);
          this.getServicios();
          this.getPaginatedServicios();
        }
      });
    } 
  
    openViewDialog(servicios: any): void {
      const dialogRef = this.dialog.open(ViewDialogComponent, {
        data: { ...servicios }
      });
    }
  
    ngOnInit(): void {
      this.proyectoId = +this.route.snapshot.paramMap.get('id')!;
      this.getServicios();
    }
  
    getPaginatedServicios(): void {
      if (this.servicios.length > 0) {
        const startIndex = (this.currentPage - 1) * this.pageSize;
        const endIndex = Math.min(startIndex + this.pageSize, this.servicios.length); // Ajuste de límites
        this.paginatedServicios = this.servicios.slice(startIndex, endIndex);
      } else {
        this.paginatedServicios = [];
      }
    }
    
    changePage(newPage: number): void {
      if (newPage > 0 && newPage <= Math.ceil(this.servicios.length / this.pageSize)) {
        this.currentPage = newPage;
        console.log('Página actual:', this.currentPage); // Registra la página actual
        this.getPaginatedServicios();
        console.log('Servicios paginados:', this.paginatedServicios); // Verifica los datos
      }
    }
  
    getServicios(): void {
      this.serviciosService.getServicios(this.proyectoId).subscribe(
        (data) => {
          this.servicios = data;
          this.getPaginatedServicios();
        },
        (error) => {
          console.error('Error al obtener Servicios:', error);
        }
      );
    }
}
