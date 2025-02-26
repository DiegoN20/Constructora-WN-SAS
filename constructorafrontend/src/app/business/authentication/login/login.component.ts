import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

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

  constructor(private authService: AuthService, private router: Router){

  }

  login(): void{
    this.authService.login(this.user, this.password).subscribe({
      next: () => this.router.navigate(['/dashboard']),
      error: (err) => console.error('Login failed', err)
    });
  }

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }
}
