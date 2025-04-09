import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { ServiciosService } from '../../../core/services/servicios.service';
 

@Component({
  selector: 'app-edit-dialog-component',
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-dialog.component.html',
  styleUrl: './edit-dialog.component.css'
})
export class EditDialogComponent {
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
  constructor(
    public dialogRef: MatDialogRef<EditDialogComponent>,
    private serviciosService: ServiciosService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    if (this.data) {
      // Asigna directamente los valores de `data` a las propiedades específicas de `asignacion`
      this.servicio.proveedor.idProveedores = this.data.idProveedores;
      this.servicio.proyecto.idProyectos = this.data.idProyecto;
      this.servicio.descripcionServicio = this.data.descripcion;
      this.servicio.costo = this.data.costo;
      this.servicio.fechaInicio = this.data.fechaInicio; // Si existe, asigna; si no, usa ''
      this.servicio.fechaFin = this.data.fechaFin; // Igual para `fechaFin`
      this.servicio.personaEncargada = this.data.personaEncargada || ''; // Igual para el estado
      this.servicio.telefono = this.data.telefono || '';
  
      // Opcional: log para asegurarte de que los valores se están asignando correctamente
    }
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
      this.toastr.error('La descripción debe tener hasta 200 caracteres y ser valido.', 'Error', {
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
    this.serviciosService.updateServicio(this.servicio).subscribe({
      next: (response) => {
        this.toastr.success('Servicio modificado exitosamente.', 'Éxito', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(response);
      },
      error: (err) => {
        console.error('Error al modificar servicio:', err);
        this.toastr.error('Error al modificar servicio.', 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
      }
    });
  }

  onCancel(): void {
    console.log(this.servicio);

    this.dialogRef.close();
  }
}
