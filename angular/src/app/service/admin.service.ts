import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { UserDetail } from '../classes/user-detail';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  // Base URL
  private  baseUrl = "http://localhost:8080/user/login";

  constructor(private http: HttpClient) { }

  login(userDetail: UserDetail): Observable<any>{
    const headers = { 'Content-Type': 'application/x-www-form-urlencoded' };
    const body = {
      username: userDetail.username,
      password: userDetail.password
    }
    return this.http.post(this.baseUrl + "/api", userDetail).pipe(catchError(this.handleError));
  }

  logout(){
    localStorage.removeItem('isUserLoggedIn');
  }

  handleError(error: HttpErrorResponse) {
    return throwError(error);
}
}
