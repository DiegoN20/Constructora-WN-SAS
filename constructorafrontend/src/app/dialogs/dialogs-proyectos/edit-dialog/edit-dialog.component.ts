import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { ProyectosService } from '../../../core/services/proyectos.service';
import { AuthService } from '../../../core/services/auth.service';
 

@Component({
  selector: 'app-edit-dialog-component',
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-dialog.component.html',
  styleUrl: './edit-dialog.component.css'
})
export class EditDialogComponent {
  user: any;
  proyecto = {
    nombreProyecto: '',
    direccion: '',
    descripcion: '',
    fechaInicio: '',
    fechaFin: '',
    presupuestoPrevisto: null,
    estadoProyecto: '',
    cantidadPisos: null,
    usuario: {
      "id_usuarios": null
    }
  }

  constructor(
    public dialogRef: MatDialogRef<EditDialogComponent>,
    private proyectosService: ProyectosService,
    private authService: AuthService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getUserData()
    if (this.data) {
      // Combina las propiedades de `data` en `proyecto` sin eliminar `usuario`
      this.proyecto = {
        ...this.proyecto, // Mantén las propiedades originales del objeto
        ...this.data // Sobrescribe únicamente las propiedades que vienen de `data`
      };
    }
  }

  validateProyecto(): boolean {
    if (!this.proyecto.nombreProyecto || this.proyecto.nombreProyecto.length > 45) {
      this.toastr.error('El nombre del proyecto es obligatorio y no debe exceder los 45 caracteres.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.proyecto.direccion || this.proyecto.direccion.length > 45) {
      this.toastr.error('El direccion del proyecto es obligatorio y no debe exceder los 45 caracteres.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.proyecto.descripcion || this.proyecto.descripcion.length > 200) {
      this.toastr.error('La descripción debe tener hasta 200 caracteres.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.proyecto.fechaInicio && !this.isFechaValida(this.proyecto.fechaInicio)) {
      this.toastr.error('La fecha de inicio no es válida.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (this.proyecto.fechaFin && !this.isFechaValida(this.proyecto.fechaFin)) {
      this.toastr.error('La fecha de fin no es válida.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.isPresupuestoValido(this.proyecto.presupuestoPrevisto)) {
      this.toastr.error('La descripción debe tener hasta 200 caracteres.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (this.user && this.user.id) {
      this.proyecto.usuario.id_usuarios = this.user.id; // Asignar el ID al objeto proyecto
    } else {
      this.toastr.error('No se pudo obtener el usuario.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
    }  
    
    return true;
  }

  isFechaValida(fecha: string): boolean {
    const regex = /^\d{4}-\d{2}-\d{2}$/; // Formato AAAA-MM-DD
    return regex.test(fecha); // Verifica si la fecha es válida
  }

  isPresupuestoValido(presupuesto: number | null): boolean {
    if (presupuesto) {
      presupuesto > 0;
      return true;
    } else {
      return false;
    }
  }

  onSave(): void {
    if (!this.validateProyecto()) {
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
  
    this.proyectosService.updateProyecto(this.proyecto).subscribe({
      next: (response) => {
        this.toastr.success('Proyecto modificado exitosamente.', 'Éxito', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(response);
      },
      error: (err) => {
        console.error('Error al modificar proyecto:', err);
        this.toastr.error('Error al modificar proyecto.', 'Error', {
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
