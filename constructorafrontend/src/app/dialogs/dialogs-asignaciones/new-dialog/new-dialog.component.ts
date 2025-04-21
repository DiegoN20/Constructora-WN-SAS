import { Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { AsignacionesService } from '../../../core/services/asignaciones.service';
import { MaestrosService } from '../../../core/services/maestros.service';

@Component({
  selector: 'app-new-dialog',
  imports: [CommonModule,FormsModule],
  templateUrl: './new-dialog.component.html',
  styleUrl: './new-dialog.component.css'
})
export class NewDialogComponent {
  proyectoId!: number;
  maestrosDisponibles: any[] = [];
  asignacion = {
    maestro: {
        id_maestros_de_obra: null
    },
    proyecto: {
        idProyectos: null as number | null
    },
    fechaAsignacion: '',
    fechaFin: '',
    estadoAsignacion: ''
  };

  constructor(public dialogRef: MatDialogRef<NewDialogComponent>,
    private asignacionesService: AsignacionesService,
    private maestrosService: MaestrosService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public data: any // Esto debe estar bien definido
  ) {}
    // Inicializamos los datos si no están presentes
  ngOnInit(): void {
    this.cargarMaestrosDisponibles();
    this.proyectoId = this.data.proyectoId;
    this.asignacion.proyecto.idProyectos = this.proyectoId;
  }

  cargarMaestrosDisponibles(): void {
    this.maestrosService.getMaestrosDisponibles().subscribe({
      next: (data) => {
        this.maestrosDisponibles = data; // Almacena los datos recibidos
      },
      error: (err) => {
        console.error('Error al obtener maestros disponibles:', err);
        this.toastr.error('Error al cargar los maestros disponibles.', 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
      }
    });
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
    this.asignacionesService.createAsignacion(this.asignacion).subscribe({
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
    console.log(this.asignacion);
    console.log();
    this.dialogRef.close();
  }
}