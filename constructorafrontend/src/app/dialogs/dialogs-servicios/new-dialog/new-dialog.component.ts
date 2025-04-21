import { Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ServiciosService } from '../../../core/services/servicios.service';
import { ProveedoresService } from '../../../core/services/proveedores.service';

@Component({
  selector: 'app-new-dialog',
  imports: [CommonModule,FormsModule],
  templateUrl: './new-dialog.component.html',
  styleUrl: './new-dialog.component.css'
})
export class NewDialogComponent {
  proyectoId!: number;
  proveedores: any[] = [];
  servicio = {
    proveedor: {
        idProveedores: null as number | null
    },
    proyecto: {
        idProyectos: null as number | null
    },
    descripcionServicio: '',
    costo: null,
    fechaInicio: '',
    fechaFin: '',
    personaEncargada: '',
    telefono: null
  };

  constructor(public dialogRef: MatDialogRef<NewDialogComponent>,
    private serviciosService: ServiciosService,
    private proveedoresService: ProveedoresService,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public data: any // Esto debe estar bien definido
  ) {}
    // Inicializamos los datos si no están presentes
  ngOnInit(): void {
    this.cargarProveedoresDisponibles();
    this.proyectoId = this.data.proyectoId;
    this.servicio.proyecto.idProyectos = this.proyectoId;
  }

  cargarProveedoresDisponibles(): void {
    this.proveedoresService.getProveedores().subscribe({
      next: (data) => {
        this.proveedores = data; // Almacena los datos recibidos
      },
      error: (err) => {
        console.error('Error al obtener los proveedores:', err);
        this.toastr.error('Error al cargar los proveedores.', 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
      }
    });
  }


  validateServicio(): boolean {
    if (!this.servicio.fechaInicio && !this.isFechaValida(this.servicio.fechaInicio)) {
      this.toastr.error('La fecha de inicio no es válida.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.isFechaFinValida(this.servicio.fechaFin)) {
      this.toastr.error('La fecha de fin no es válida.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.servicio.descripcionServicio || this.servicio.descripcionServicio.length > 200) {
      this.toastr.error('La descripción debe tener hasta 200 caracteres y ser valido.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.isTelefonoValido(this.servicio.telefono)) {
      this.toastr.error('El telefono debe ser valido.', 'Error', {
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

  isTelefonoValido(telefono: number | null): boolean {
    if (!telefono) {
      return true; // Si no hay teléfono, lo consideramos válido
    }
    const telefonoStr = telefono.toString(); // Convertimos el número a string
    return /^[0-9]{1,10}$/.test(telefonoStr); // Validamos con la regex
  }

  onSave(): void {
    
    if (!this.validateServicio()) {
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
    if (!this.servicio.proveedor.idProveedores) {
      this.toastr.error('Debe seleccionar un maestro.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return;
    }
    this.serviciosService.createServicio(this.servicio).subscribe({
      next: (response) => {
        this.toastr.success('Servicio creado exitosamente.', 'Éxito', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(response);
      },
      error: (err) => {
        console.error('Error al crear servicio:', err);
        this.toastr.error('Error al crear servicio.', 'Error', {
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