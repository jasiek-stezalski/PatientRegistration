import {AfterViewInit, Component} from '@angular/core';
import {ClinicService} from '../../../services/clinic.service';
import {VisitModelService} from '../../../services/visitModel.service';
import {VisitService} from '../../../services/visit.services';

@Component({
  selector: 'app-searchVisits',
  templateUrl: './searchVisits.component.html',
  styleUrls: ['./searchVisits.component.css']
})
export class SearchVisitsComponent implements AfterViewInit {

  careTypes: String[] = ['Publiczna', 'Prywatna'];
  cities: Set<String> = new Set<String>();
  specializations: String[] = this.visitModelService.specialization;

  careType: String;
  city: String;
  specialization: String;

  constructor(private clinicService: ClinicService, private visitModelService: VisitModelService,
              private visitService: VisitService) {
  }

  ngAfterViewInit(): void {
    this.clinicService.getClinics()
      .subscribe(data => {
        data.forEach(i => this.cities.add(i.city));
      });

  }

  submit() {
    this.visitService.getVisitsByVisitFilter(this.careType, this.city, this.specialization)
      .subscribe(data => {
        console.log(data);
      });
  }

}
