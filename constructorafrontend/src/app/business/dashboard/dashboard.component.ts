import { Component } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-dashboard',
  imports: [],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export default class DashboardComponent {
  user: any;
  
  constructor(private authService: AuthService){}
  
  ngOnInit(): void {
    this.user = this.authService.getUserData(); // Obtenemos datos como nombre y rol
  }
}
