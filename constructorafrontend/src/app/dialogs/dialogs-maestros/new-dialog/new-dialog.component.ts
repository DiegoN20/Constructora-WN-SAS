import { Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MaestrosService } from '../../../core/services/maestros.service';
import { ToastrService } from 'ngx-toastr';
import { CommonModule } from '@angular/common';
import { Console } from 'console';

@Component({
  selector: 'app-new-dialog',
  imports: [CommonModule,FormsModule],
  templateUrl: './new-dialog.component.html',
  styleUrl: './new-dialog.component.css'
})
export class NewDialogComponent {
  maestro = {
    cedula: null,
    nombre: '',
    apellido: '',
    telefono: null,
    estadoMaestro: 'Disponible',
    salario: null,
    fechaVinculacion: '',
    fechaDesvinculacion: ''
  };

  constructor(public dialogRef: MatDialogRef<NewDialogComponent>,
    private maestrosService: MaestrosService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public data: any // Esto debe estar bien definido
  ) {}
    // Inicializamos los datos si no están presentes
  ngOnInit(): void {
  }

  validateMaestro(): boolean {
    if (!this.maestro.cedula){
      this.toastr.error('La cedula del Maestro es obligatoria.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }

    if (!this.maestro.nombre || this.maestro.nombre.length > 45) {
      this.toastr.error('El nombre del Maestro es obligatorio y no debe exceder los 45 caracteres.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.maestro.apellido || this.maestro.apellido.length > 45) {
      this.toastr.error('El apellido del Maestro es obligatorio y no debe exceder los 45 caracteres.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (this.maestro.telefono && !this.isTelefonoValido(this.maestro.telefono)) {
      this.toastr.error('El número de teléfono debe ser válido y tener hasta 10 dígitos.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (this.maestro.salario && !this.isSalarioValido(this.maestro.salario)) {
      this.toastr.error('El salario no es válido.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (this.maestro.fechaVinculacion && !this.isFechaValida(this.maestro.fechaVinculacion)) {
      this.toastr.error('La fecha no es válida.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (this.maestro.fechaDesvinculacion && !this.isFechaValida(this.maestro.fechaDesvinculacion)) {
      this.toastr.error('La fecha no es válida.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    
    return true;
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

  isCedulaValida(cedula: number | null): boolean {
    if (cedula) {
      const cedulaStr = cedula.toString(); // Convertimos el número a string
      return /^[0-9]{1,10}$/.test(cedulaStr); // Si no hay teléfono, lo consideramos válido
    }else{
      return false;
    }
  }

  isSalarioValido(salario: number | null): boolean {
    if (salario) {
      salario > 0;
      return true;
    } else {
      return false;
    }
  }

  onSave(): void {
    if (!this.validateMaestro()) {
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
  
    this.maestrosService.createMaestro(this.maestro).subscribe({
      next: (response) => {
        this.toastr.success('Maestro creado exitosamente.', 'Éxito', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(response);
      },
      error: (err) => {
        console.error('Error al crear Maestro:', err);
        this.toastr.error('Error al crear Maestro.', 'Error', {
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