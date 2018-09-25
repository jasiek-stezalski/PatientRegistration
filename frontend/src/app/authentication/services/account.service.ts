import {Injectable} from '@angular/core';
import {User} from '../../models/user.model';
import {HttpClient} from '@angular/common/http';


@Injectable()
export class AccountService {

  private url = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  createAccount(user: User) {
    return this.http.post(this.url + '/account/register', user);
  }
}
