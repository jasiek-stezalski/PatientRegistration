import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {ClinicService} from '../../../services/clinic.service';
import {VisitModelService} from '../../../services/visitModel.service';
import {VisitService} from '../../../services/visit.services';
import {Visit} from "../../../models/visit.model";
import {BookComponent} from "../book/book.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-searchVisits',
  templateUrl: './searchVisits.component.html',
  styleUrls: ['./searchVisits.component.css']
})
export class SearchVisitsComponent implements AfterViewInit {

  @ViewChild('book') book: BookComponent;

  visits: Visit[] = [];

  isVisible: boolean;

  careTypes: String[] = ['Publiczna', 'Prywatna'];
  cities: Set<String> = new Set<String>();
  specializations: String[] = this.visitModelService.specialization;

  careType: String;
  city: String;
  specialization: String;

  constructor(private clinicService: ClinicService, private visitModelService: VisitModelService,
              private visitService: VisitService, private router: Router) {
  }

  ngAfterViewInit(): void {
    this.clinicService.getClinics()
      .subscribe(data => {
        data.forEach(i => this.cities.add(i.city));
      });

    this.isVisible = false;
  }

  submit() {
    this.visitService.getVisitsByVisitFilterLimited(this.careType, this.city, this.specialization)
      .subscribe(data => {
        this.visits = data;
      });
    this.isVisible = true;
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
    this.router.navigate(['/patientCalendar'], {
      queryParams: {
        careType: this.careType,
        city: this.city,
        specialization: this.specialization
      }
    });
  }
}
