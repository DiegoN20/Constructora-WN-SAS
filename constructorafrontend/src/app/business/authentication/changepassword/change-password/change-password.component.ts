import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EmailPasswordService } from '../../../../core/services/email-password.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-change-password',
  imports: [FormsModule, CommonModule],
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.css'
})
export default class ChangePasswordComponent implements OnInit{

  password: string = '';
  confirmPassword: string = '';
  passwordWeak: boolean = false;
  passwordMismatch: boolean = false;
  showNewPassword: boolean = false;
  showConfirmPassword: boolean = false;
  tokenPassword: string= '';

  constructor(private emailPasswordService: EmailPasswordService, private toastrService: ToastrService, private router: Router, private activatedRoute: ActivatedRoute){}


  ngOnInit(): void { 
  }

  onChangePassword(): void {
    this.tokenPassword = this.activatedRoute.snapshot.params['tokenPassword'];

    if (this.password !== this.confirmPassword) {
      this.toastrService.error('Las contraseñas no coinciden', 'FAIL', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return;
    }
    this.emailPasswordService.changePassword(this.password, this.confirmPassword, this.tokenPassword).subscribe(
      (data: any) => {
        this.toastrService.success(data.mensaje, 'OK', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.router.navigate(['/login']);
      },
      (err: any) => {
        this.toastrService.error(err.error.mensaje, 'FAIL', {
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
      }
    );
  }

  validatePassword() {
    const password = this.password;
    const hasUpperCase = /[A-Z]/.test(password);
    const hasNumber = /\d/.test(password);
    const hasMinLength = password.length >= 8;

    this.passwordWeak = !(hasUpperCase && hasNumber && hasMinLength);
  }

  checkPasswords() {
    this.passwordMismatch = this.password !== this.confirmPassword;
  }

  clearConfirmPassword() {
    this.password = '';
    this.confirmPassword = '';
    this.passwordMismatch = false;
  }

  discardChanges() {
    this.password = '';
    this.confirmPassword = '';
    this.passwordWeak = false;
    this.passwordMismatch = false;
    window.close();
  }

  toggleNewPassword(): void {
    this.showNewPassword = !this.showNewPassword;
    const newPasswordInput = document.getElementById('newPassword') as HTMLInputElement;
    if (newPasswordInput) {
      newPasswordInput.type = this.showNewPassword ? 'text' : 'password';
    }
  }

  toggleConfirmPassword(): void {
    this.showConfirmPassword = !this.showConfirmPassword;
    const confirmPasswordInput = document.getElementById('confirmPassword') as HTMLInputElement;
    if (confirmPasswordInput) {
      confirmPasswordInput.type = this.showConfirmPassword ? 'text' : 'password';
    }
  }

  onSubmit(form: NgForm) {
    this.validatePassword();
    this.checkPasswords();

    if (this.passwordWeak || this.passwordMismatch) {
      return;
    }

    // Aquí puedes agregar la lógica para enviar la nueva contraseña al backend
    alert('Contraseña cambiada exitosamente');
    form.resetForm();
  }
}