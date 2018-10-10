import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Clinic} from '../models/clinic.model';
import {Observable} from 'rxjs';

@Injectable()
export class ClinicService {

  private url = 'http://localhost:8080/clinics/';

  constructor(private http: HttpClient) {
  }

  getClinics(): Observable<Clinic[]> {
    return this.http.get(this.url) as Observable<Clinic[]>;
  }

  getClinicByIdUser(idUser: String | Number): Observable<Clinic[]> {
    return this.http.get(this.url + 'user?idUser=' + idUser) as Observable<Clinic[]>;
  }

}


