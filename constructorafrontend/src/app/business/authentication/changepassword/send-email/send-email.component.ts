import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { EmailPasswordService } from '../../../../core/services/email-password.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-send-email',
  imports: [RouterLink, FormsModule, CommonModule],
  templateUrl: './send-email.component.html',
  styleUrl: './send-email.component.css'
})
export default class SendEmailComponent implements OnInit{
  mailTo: string = '';

  constructor(private emailPasswordService: EmailPasswordService, private toastrService: ToastrService, private router: Router){}
  
  ngOnInit(): void {
  }

  onSendEmail(): void{
    this.emailPasswordService.sendEmail(this.mailTo).subscribe(
      (data: any) => {
        if(data.mensaje === 'Te hemos enviado un correo'){
          this.toastrService.success(data.mensaje, 'OK', {
            timeOut: 3000, positionClass: 'toast-top-center'
          });
          this.router.navigate(['/login']);
        } else {
          this.toastrService.success(data.mensaje, 'OK', {
            timeOut: 3000, positionClass: 'toast-top-center'
          });
        }
      },
      (err: any) => {
        const errorMessage = err?.error?.mensaje || 'Ocurrió un error inesperado';
        this.toastrService.error(errorMessage, 'FAIL', {
        timeOut: 3000,
        positionClass: 'toast-top-center',
      });
      }
    );
  }

}
