import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmailPasswordService {

  private CHANGEPASSWORD_URL = 'http://localhost:3000/email-password/';

  constructor(private httpClient: HttpClient) { }

  sendEmail(mailTo: string): Observable<any> {
    return this.httpClient.post<any>(`${this.CHANGEPASSWORD_URL}send-email`, { mailTo })
      .pipe(
        catchError(this.handleError)
      );
  }

  changePassword(password: string, confirmPassword: string, tokenPassword: string): Observable<any> {
    return this.httpClient.post<any>(`${this.CHANGEPASSWORD_URL}change-password`, { password, confirmPassword, tokenPassword })
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      if (error.status === 404) {
        errorMessage = 'Recurso no encontrado (404).';
      } else {
        errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
      }
    }
    return throwError(errorMessage);
  }
}