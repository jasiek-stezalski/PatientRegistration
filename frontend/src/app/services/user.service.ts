import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/map';

import {User} from '../models/user.model';
import {Headers, Http, RequestOptions, Response} from '@angular/http';
import {Observable} from 'rxjs';

@Injectable()
export class UserService {

  constructor(private httpClient: HttpClient, public http: Http) {
  }

  private url = 'http://localhost:8080/users/';

  getUsersByRole(role: string): Observable<User[]> {
    return this.httpClient.get(this.url + 'role/' + role) as Observable<User[]>;
  }

  public getUsersByIdDoctor(idDoctor: string | number): Observable<User[]> {
    return this.httpClient.get(this.url + 'doctor/' + idDoctor) as Observable<User[]>;
  }

  getUserById(idUser: string | number): Observable<User> {
    return this.httpClient.get(this.url + idUser) as Observable<User>;
  }

  public createUser(user: User): Observable<User> {
    return this.httpClient.post(this.url, user) as Observable<User>;
  }

  public logIn(user: User) {

    let headers = new Headers();
    headers.append('Accept', 'application/json');
    // creating base64 encoded String from user name and password
    var base64Credential: string = btoa(user.username + ':' + user.password);
    headers.append('Authorization', 'Basic ' + base64Credential);

    let options = new RequestOptions();
    options.headers = headers;

    return this.http.get(this.url + 'login', options)
      .map((response: Response) => {
        // login successful if there's a jwt token in the response
        let user = response.json().principal;// the returned user object is a principal object
        if (user) {
          // store user details  in session storage to keep user logged in between page refreshes
          sessionStorage.setItem('currentUser', JSON.stringify(user));
        }
      });
  }

  logOut() {
    sessionStorage.removeItem('currentUser');
    return this.http.post(this.url + 'logout', {});
  }

}
