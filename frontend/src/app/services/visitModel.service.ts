import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {DayPilot} from 'daypilot-pro-angular';
import {HttpClient} from '@angular/common/http';
import {VisitModel} from '../models/visitModel.model';

@Injectable()
export class VisitModelService {

  private url = 'http://localhost:8080/visitModels/';

  constructor(private http: HttpClient) {
  }

  getVisitModelsInWeekByDoctor(from: DayPilot.Date, to: DayPilot.Date, idUser: string | number): Observable<VisitModel[]> {
    return this.http.get(this.url + 'doctor/' + idUser + '?from=' + from.toString() + '&to=' + to.toString()) as Observable<VisitModel[]>;
  }

  createVisitModel(data): Observable<VisitModel> {
    return this.http.post(this.url, data) as Observable<VisitModel>;
  }

  moveVisitModel(data: any): Observable<VisitModel> {
    return this.http.put(this.url, data) as Observable<VisitModel>;
  }

  deleteVisitModel(idVisitModel: string) {
    return this.http.delete(this.url + idVisitModel);
  }

  public static minimalDate() {
    let today = new Date();
    let dd;
    let mm;
    if (today.getDate() < 10) {
      dd = '0' + today.getDate();
    } else {
      dd = today.getDate();
    }
    if (today.getMonth() + 1 < 10) {
      mm = '0' + (today.getMonth() + 1);
    } else {
      mm = today.getMonth() + 1;
    }

    return today.getFullYear() + '-' + mm + '-' + dd;
  }

  specialization: string[] = [
    'Alergolog',
    'Dermatolog',
    'Diabetolog',
    'Ginekolog',
    'Kardiolog',
    'Lekarz rodzinny',
    'Okulista',
    'Psychiatra',
    'Stomatolog',
    'Urolog',
  ];

}


