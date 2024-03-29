import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {ClinicService} from '../../../services/clinic.service';
import {VisitModelService} from '../../../services/visitModel.service';
import {VisitService} from '../../../services/visit.services';
import {Visit} from "../../../models/visit.model";
import {BookComponent} from "../book/book.component";
import {Router} from "@angular/router";
import {Clinic} from "../../../models/clinic.model";
import {isUndefined} from "util";

@Component({
  selector: 'app-searchVisits',
  templateUrl: './searchVisits.component.html',
  styleUrls: ['./searchVisits.component.css']
})
export class SearchVisitsComponent implements AfterViewInit {

  @ViewChild('book') book: BookComponent;

  visits: Visit[] = [];

  isVisible: boolean = false;
  isVisible2: boolean = true;
  info: string;
  info2: string;

  careTypes: string[] = ['Publiczna', 'Prywatna'];
  cities: Set<string> = new Set<string>();
  clinicsBase: Clinic[] = [];
  clinics: Clinic[] = [];
  specializations: string[] = this.visitModelService.specialization;

  careType: string;
  city: string;
  clinic: Clinic;
  specialization: string;

  constructor(private clinicService: ClinicService, private visitModelService: VisitModelService,
              private visitService: VisitService, private router: Router) {
  }

  ngAfterViewInit(): void {
    this.clinicService.getClinics()
      .subscribe(data => {
        this.clinicsBase = data;
        this.clinics = data;

        data.forEach(i => this.cities.add(i.city));
      });

  }

  submit() {
    this.visitService.getVisitsByVisitFilterLimited(this.careType, this.city, isUndefined(this.clinic) ? -1 : this.clinic.id, this.specialization)
      .subscribe(data => {
        this.visits = data;

        if (this.visits.length === 0) {
          this.isVisible = false;
          this.info = 'Brak pasujących wizyt!';
        }
        else {
          this.isVisible = true;
          this.info = '';
        }

      });
    this.isVisible2 = false;
  }

  bookVisit(visit: Visit) {
    this.book.show(visit);
  }

  createClosed(args) {
    if (args.result) {
      alert('Zostałeś zapisany na wizytę!');
      this.router.navigate(['/userVisits']);
    }
  }

  openCalendar() {
    if (isUndefined(this.clinic))
      this.info2 = 'Wybierz placówkę';
    else {
      this.router.navigate(['/patientCalendar'], {
        queryParams: {
          careType: this.careType,
          city: this.city,
          idClinic: this.clinic.id,
          specialization: this.specialization
        }
      });
    }
  }

  changeCity(val) {
    this.clinics = [];
    this.clinics = this.clinicsBase.filter(v => v.city === val);
  }

}
