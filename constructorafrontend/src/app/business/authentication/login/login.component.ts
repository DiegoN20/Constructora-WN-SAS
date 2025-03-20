import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export default class LoginComponent {
  user: string = '';
  password: string ='';
  showPassword: boolean = false;
  isEmailValid: boolean = true;

  constructor(private authService: AuthService, private router: Router, private toastrService: ToastrService ){

  }

  login(): void {
    if (!this.validateEmail(this.user)) {
      this.toastrService.error('El correo electrónico no tiene un formato válido', 'FAIL', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
      });
      return;
    }
    this.authService.login(this.user, this.password).subscribe({
      next: (response)=> {
        const token = response.token;
        const payload = JSON.parse(atob(token.split('.')[1]));
        const role = payload.rol;
        if(role === 'contratista') {
          this.router.navigate(['/dashboard'])
        }else {
          this.router.navigate(['/login'])
        }
      },
      error: (err) => this.toastrService.error('Contraseña o correo errados', 'FAIL', {
        timeOut: 3000,
        positionClass: 'toast-top-center'
        }),
      }
    );
  }

  validateEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }

  onEmailInput(): void {
    this.isEmailValid = this.validateEmail(this.user);
  }

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }
  
}
