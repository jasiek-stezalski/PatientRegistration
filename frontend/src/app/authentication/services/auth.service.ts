import {Injectable} from '@angular/core';
import {Headers, RequestOptions, Response} from '@angular/http';
import {map} from 'rxjs/operators';
import {User} from '../../models/user.model';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class AuthService {

  private url = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  public logIn(user: User) {

    let headers = new Headers();
    headers.append('Accept', 'application/json');
    // creating base64 encoded String from user name and password
    var base64Credential: string = btoa(user.username + ':' + user.password);
    headers.append('Authorization', 'Basic ' + base64Credential);

    let options = new RequestOptions();
    options.headers = headers;

    return this.http.get(this.url + '/account/login').pipe(map((response: Response) => {
      // login successful if there's a jwt token in the response
      let user = response.json().principal;// the returned user object is a principal object
      if (user) {
        // store user details  in local storage to keep user logged in between page refreshes
        localStorage.setItem('currentUser', JSON.stringify(user));
      }
    }));
  }

  logOut() {
    // remove user from local storage to log user out
    return this.http.post(this.url + 'logout', {}).pipe(map(() => {
      localStorage.removeItem('currentUser');
    }));

  }
}
