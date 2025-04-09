import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  user: any;

  constructor(private authService: AuthService){}

  ngOnInit(): void {
    this.user = this.authService.getUserData(); // Obtenemos datos como nombre y rol
  }
  
    logout(): void{
      this.authService.logout()
    }

}
