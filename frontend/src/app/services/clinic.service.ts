import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Clinic} from '../models/clinic.model';

@Injectable()
export class ClinicService {

  private url = 'http://localhost:8080/clinics/';

  constructor(private http: HttpClient) {
  }

  getClinics() {
    return this.http.get<Clinic[]>(this.url);
  }

  getClinicByIdUser(idUser : String | Number) {
    return this.http.get<Clinic[]>(this.url + 'user?idUser=' + idUser);
  }

}


