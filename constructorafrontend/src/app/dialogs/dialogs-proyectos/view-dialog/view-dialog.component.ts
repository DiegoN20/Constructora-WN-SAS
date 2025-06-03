import { Component, Inject } from '@angular/core';
import { NewDialogComponent } from '../new-dialog/new-dialog.component';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ProyectosService } from '../../../core/services/proyectos.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-view-dialog-component',
  imports: [],
  templateUrl: './view-dialog.component.html',
  styleUrl: './view-dialog.component.css'
})
export class ViewDialogComponent {

  constructor(public dialogRef: MatDialogRef<NewDialogComponent>,
      private proyectosService: ProyectosService,
      private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: any){}

  onCancel(): void {
    this.dialogRef.close();
  }

  // Función para redirigir a la pestaña de Asignaciones
  onAsignaciones(id: number): void {
    this.router.navigate([`/proyectos/${id}/asignaciones`]);
    this.dialogRef.close();
  }

  // Función para redirigir a la pestaña de Servicios
  onServicios(id: number): void {
    this.router.navigate([`/proyectos/${id}/servicios`]);
    this.dialogRef.close();
  }

  // Función para redirigir a la pestaña de Inventario Inicial
  onInventario(id: number): void {
    this.router.navigate([`/proyectos/${id}/inventarios`]);
    this.dialogRef.close();
  }

  // Función para redirigir a la pestaña de Avances
  onAvances(id: number): void {
    this.router.navigate([`/proyectos/${id}/avances`]);
    this.dialogRef.close();
  }

  // Función para redirigir a la pestaña de Stock
  onStocks(id: number): void {
    this.router.navigate([`/proyectos/${id}/stock`]);
    this.dialogRef.close();
  }

}
