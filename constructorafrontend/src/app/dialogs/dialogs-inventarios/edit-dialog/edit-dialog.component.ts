import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { InventariosService } from '../../../core/services/inventarios.service';

@Component({
  selector: 'app-edit-dialog-component',
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-dialog.component.html',
  styleUrl: './edit-dialog.component.css'
})
export class EditDialogComponent {
  proyectoId!: number;
  insumos: any[] = [];
  inventario = {
    idInventarioInicial: null,
    proyecto: {
      idProyectos: null as number | null
    },
    insumo: {
      idInsumos: null as number | null
    },
    cantidad: null,
    precio: null,
    unidad: ''
  };

  constructor(
    public dialogRef: MatDialogRef<EditDialogComponent>,
    private inventariosService: InventariosService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    if (this.data) {
      this.inventario.idInventarioInicial = this.data.idInventario;
      this.inventario.proyecto.idProyectos = this.data.proyecto;
      this.inventario.insumo.idInsumos = this.data.insumo;
      this.inventario.cantidad = this.data.cantidad; // Si existe, asigna; si no, usa ''
      this.inventario.precio = this.data.precio; // Igual para `fechaFin`
      this.inventario.unidad = this.data.unidad;
    }
  }

  onSave(): void {
    console.log(this.inventario)
    if (!this.validateInventario()) {
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
  
    this.inventariosService.updateInventario(this.inventario).subscribe({
      next: (response) => {
        this.toastr.success('Inventario modificado exitosamente.', 'Éxito', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(response);
      },
      error: (err) => {
        console.error('Error al modificar inventario:', err);
        this.toastr.error('Error al modificar inventario.', 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
      }
    });
  }

  onCancel(): void {
    console.log(this.data)
    console.log(this.inventario)
    this.dialogRef.close();
  }
  
  validateInventario(): boolean {
    if (!this.inventario.cantidad || !this.isValorPositivo(this.inventario.cantidad)) {
      this.toastr.error('La cantidad es obligatoria y no debe ser negativa.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (!this.inventario.precio || !this.isValorPositivo(this.inventario.precio)) {
      this.toastr.error('El precio es obligatorio y no debe ser negativa.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    if (this.inventario.unidad && this.inventario.unidad.length > 25 ) {
      this.toastr.error('La unidad no es válido.', 'Error', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return false;
    }
    return true;
  }

  isValorPositivo(numero: number | null): boolean {
    if (numero === null) {
        return false; // O maneja el caso como prefieras
    }
    return numero > 0;
  }
}
