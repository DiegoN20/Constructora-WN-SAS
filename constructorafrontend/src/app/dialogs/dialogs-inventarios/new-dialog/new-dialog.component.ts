import { Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { CommonModule } from '@angular/common';
import { InventariosService } from '../../../core/services/inventarios.service';
import { InsumosService } from '../../../core/services/insumos.service';

@Component({
  selector: 'app-new-dialog',
  imports: [CommonModule,FormsModule],
  templateUrl: './new-dialog.component.html',
  styleUrl: './new-dialog.component.css'
})
export class NewDialogComponent {
  proyectoId!: number;
  insumos: any[] = [];
  inventario = {
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

  constructor(public dialogRef: MatDialogRef<NewDialogComponent>,
    private inventariosService: InventariosService,
    private insumosService: InsumosService,
    private toastr: ToastrService,
    @Inject(MAT_DIALOG_DATA) public data: any // Esto debe estar bien definido
  ) {}
    // Inicializamos los datos si no están presentes
  ngOnInit(): void {
    this.cargarInsumos();
    this.proyectoId = this.data.proyectoId;
    this.inventario.proyecto.idProyectos = this.proyectoId;
  }

  cargarInsumos(): void {
    this.insumosService.getInsumos().subscribe({
      next: (data) => {
        this.insumos = data; // Almacena los datos recibidos
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
  
    this.inventariosService.createInventario(this.inventario).subscribe({
      next: (response) => {
        this.toastr.success('Inventario creado exitosamente.', 'Éxito', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.dialogRef.close(response);
      },
      error: (err) => {
        console.error('Error al crear inventario:', err);
        this.toastr.error('Error al crear inventario.', 'Error', {
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