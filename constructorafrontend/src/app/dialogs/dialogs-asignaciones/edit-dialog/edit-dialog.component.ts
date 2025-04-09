import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { AsignacionesService } from '../../../core/services/asignaciones.service';
 

@Component({
  selector: 'app-edit-dialog-component',
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-dialog.component.html',
  styleUrl: './edit-dialog.component.css'
})
export class EditDialogComponent {
  proyectoId!: number;
  asignacion = {
    maestro: {
        "id_maestros_de_obra": null
    },
    proyecto: {
        "idProyectos": null as number | null
    },
    fechaAsignacion: '',
    fechaFin: '',
    estadoAsignacion: ''
  };

  constructor(
    public dialogRef: MatDialogRef<EditDialogComponent>,
    private asignacionService: AsignacionesService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    if (this.data) {
      // Asigna directamente los valores de `data` a las propiedades específicas de `asignacion`
      this.asignacion.maestro.id_maestros_de_obra = this.data.maestro;
      this.asignacion.proyecto.idProyectos = this.data.proyecto;
      this.asignacion.fechaAsignacion = this.data.fechaAsignacion || ''; // Si existe, asigna; si no, usa ''
      this.asignacion.fechaFin = this.data.fechaFin || ''; // Igual para `fechaFin`
      this.asignacion.estadoAsignacion = this.data.estadoAsignacion || ''; // Igual para el estado
  
      // Opcional: log para asegurarte de que los valores se están asignando correctamente
      console.log('Asignación inicializada:', this.asignacion);
    }
  }

  validateAsignacion(): boolean {
    if (!this.asignacion.fechaAsignacion && !this.isFechaValida(this.asignacion.fechaAsignacion)) {
      this.toastr.error('La fecha de Asignación no es válida.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.isFechaFinValida(this.asignacion.fechaFin)) {
      this.toastr.error('La fecha de fin no es válida.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.asignacion.estadoAsignacion || this.asignacion.estadoAsignacion.length > 15) {
      this.toastr.error('El estado debe tener hasta 15 caracteres y ser valido.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }   
    return true;
  }

  isFechaFinValida(fechaFin: string | null): boolean {
    if (fechaFin === null || !fechaFin) {
      return true; // Si es null, es válido
    }
    const regex = /^\d{4}-\d{2}-\d{2}$/; // Formato AAAA-MM-DD
    return regex.test(fechaFin); // Verifica si la fecha es válida
  }

  isFechaValida(fecha: string): boolean {
    const regex = /^\d{4}-\d{2}-\d{2}$/; // Formato AAAA-MM-DD
    return regex.test(fecha); // Verifica si la fecha es válida
  }

  onSave(): void {
    console.log(this.asignacion)
    if (!this.validateAsignacion()) {
      return;
    }
  
    const token = localStorage.getItem('authToken');
    if (!token) {
      this.toastr.error('Sesión no válida. Por favor, inicia sesión nuevamente.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      this.dialogRef.close();
      return;
    }
    if (!this.asignacion.maestro.id_maestros_de_obra) {
      this.toastr.error('Debe seleccionar un maestro.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return;
    }
    this.asignacionService.updateAsignacion(this.asignacion).subscribe({
      next: (response) => {
        this.toastr.success('Asignación creado exitosamente.', 'Éxito', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(response);
      },
      error: (err) => {
        console.error('Error al crear asignación:', err);
        this.toastr.error('Error al crear asignación.', 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
      }
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
