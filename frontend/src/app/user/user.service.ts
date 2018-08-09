import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {User} from '../models/user.model';

@Injectable()
export class UserService {

  constructor(private http: HttpClient) {
  }

  private userUrl = 'http://localhost:8080/users';

  public getUsers() {
    return this.http.get<User[]>(this.userUrl);
  }

  public deleteUser(user) {
    return this.http.delete(this.userUrl + '/' + user.idUser);
  }

  public createUser(user) {
    return this.http.post<User>(this.userUrl, user);
  }

}
