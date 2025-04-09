import { Component, Inject } from '@angular/core';
import { NewDialogComponent } from '../new-dialog/new-dialog.component';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MaestrosService } from '../../../core/services/maestros.service';


@Component({
  selector: 'app-view-dialog-component',
  imports: [],
  templateUrl: './view-dialog.component.html',
  styleUrl: './view-dialog.component.css'
})
export class ViewDialogComponent {

  constructor(public dialogRef: MatDialogRef<NewDialogComponent>,
      private maestrosService: MaestrosService,
    @Inject(MAT_DIALOG_DATA) public data: any){}

  onCancel(): void {
    this.dialogRef.close();
  }
}
