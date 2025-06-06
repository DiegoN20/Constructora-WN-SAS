import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private userKey = 'userData';

  private LOGIN_URL = 'http://localhost:3000/api/v1/auth/login';
  private tokenKey = 'authToken';

  private REFRESH_URL = 'http://localhost:3000/api/v1/auth/refresh';
  private refreshTokenKey = 'refreshToken';

  constructor(private httpClient: HttpClient, private router: Router) { }

  login(user: string, password: string): Observable<any>{
    return this.httpClient.post<any>(this.LOGIN_URL, {user, password}).pipe(
      tap(response => {
        if(response.token){
          
          this.setToken(response.token);
          this.setRefreshToken(response.refreshToken);
          this.saveUserData({
            nombre: response.nombre,
            apellido: response.apellido,
            rol: response.rol,
            id: response.id,
            proyectosTotal: response.proyectosTotal,
            proyectosEnCurso: response.proyectosEnCurso,
            proyectosFinalizados: response.proyectosFinalizados,
            proyectosSuspendidos: response.proyectosSuspendidos,
            maestrosTotal: response.maestrosTotal,
            maestrosDisponibles: response.maestrosDisponibles,
            maestrosAsignados: response.maestrosAsignados,
            proveedoresTotal: response.proveedoresTotal
          });
          this.autoRefreshToken();
        }
      })
    )
  }

  private saveUserData(userData: any): void {
    localStorage.setItem(this.userKey, JSON.stringify(userData));
  }

  getUserData(): any {
    const userData = localStorage.getItem(this.userKey);
    return userData ? JSON.parse(userData) : null;
  }
  
  private setToken(token: string): void{
    localStorage.setItem(this.tokenKey, token);
  }

  public getToken(): string | null{
    if(typeof window !== 'undefined'){
      return localStorage.getItem(this.tokenKey);
    }else{
      return null;
    }
  }

  private setRefreshToken(token: string): void{
    localStorage.setItem(this.refreshTokenKey, token);
  }

  private getRefreshToken(): string | null{
    if(typeof window !== 'undefined'){
      return localStorage.getItem(this.refreshTokenKey);
    }else{
      return null;
    }
  }

  refreshToken(): Observable<any>{
    const refreshToken = this.getRefreshToken()
    return this.httpClient.post<any>(this.REFRESH_URL, {refreshToken}).pipe(
      tap(response => {
        if(response.token){
          this.setToken(response.token);
          this.setRefreshToken(response.refreshToken);
          this.autoRefreshToken();
        }
      })
    )
  }

  autoRefreshToken(): void{
    const token = this.getToken();
    if(!token){
      return;
    }
    const payload = JSON.parse(atob(token.split('.')[1]));
    const exp = payload.exp * 1000;

    const timeout = exp - Date.now() - (60 * 1000);

    setTimeout(() => {
      this.refreshToken().subscribe()
    }, timeout);
  }

  isAuthenticated(): boolean{
    const token = this.getToken();
    if(!token){
      return false;
    }
    const payload = JSON.parse(atob(token.split('.')[1]));
    const exp = payload.exp * 1000;
    return Date.now() < exp;
  }

  logout(): void{
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.refreshTokenKey);
    this.router.navigate(['/login']);
  }
  
}
